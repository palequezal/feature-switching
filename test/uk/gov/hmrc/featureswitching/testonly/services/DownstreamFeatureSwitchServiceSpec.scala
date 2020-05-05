package uk.gov.hmrc.featureswitching.testonly.services

import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration
import play.api.test.Helpers._
import uk.gov.hmrc.featureswitching.testonly.BaseSpec
import uk.gov.hmrc.featureswitching.testonly.config.TestFeatureSwitches.{TestFs1, TestFs2, TestFs3}
import uk.gov.hmrc.featureswitching.testonly.connectors.mocks.MockDownstreamFeatureswitchConnector
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DownstreamFeatureSwitchServiceSpec extends BaseSpec {

  val configKey = "feature-switches.services"
  val testServiceUrl1 = "/test/feature-switches"
  val testServiceUrl2 = "/test-two/feature-switches"
  val testServiceUrls = testServiceUrl1 ++ testServiceUrl2
  val configWithUrls = Configuration.from(Map(configKey -> s"$testServiceUrl1,$testServiceUrl2"))
  val configWithOneUrl = Configuration.from(Map(configKey -> testServiceUrl1))


  val fsResponse1: FeatureSwitchSettingMap = Map(testServiceUrl1 -> Set(
    FeatureSwitchSetting(TestFs1.name, TestFs1.displayName, true),
    FeatureSwitchSetting(TestFs2.name, TestFs2.displayName, false)))

  val fsResponse2: FeatureSwitchSettingMap = Map(testServiceUrl2 -> Set(
    FeatureSwitchSetting(TestFs3.name, TestFs3.displayName, true)
  ))

  class Setup(config: Configuration = Configuration()) {
    val service = new DownstreamFeatureSwitchService(config, MockDownstreamFeatureswitchConnector.mockDownstreamFsConnector)
    val connector = MockDownstreamFeatureswitchConnector
  }

  "The Downstream Feature Switch Service" when {
    "the config contains a two downstream service URLs" should {
      "return feature switches from this service and two others" in new Setup(configWithUrls) {
        connector.stubGetFeatureSwitches(testServiceUrl1)(Future.successful(fsResponse1))
        connector.stubGetFeatureSwitches(testServiceUrl2)(Future.successful(fsResponse2))

        val result = await(service.getDownstreamFeatureSwitches)

        result shouldBe fsResponse1 ++ fsResponse2
      }
    }
    "the config contains a single URL" should {
      "return feature switches for this service and the other service" in new Setup(configWithOneUrl) {
        connector.stubGetFeatureSwitches(testServiceUrl1)(Future.successful(fsResponse1))

        val result = await(service.getDownstreamFeatureSwitches)

        result shouldBe fsResponse1
      }
    }
    "the config key is not set" should {
      "return an empty map" in new Setup() {
        val result = await(service.getDownstreamFeatureSwitches)

        result shouldBe Map.empty
      }
    }
    "the connector fails to return any feature switches" should {
      "throw an exception" in new Setup(configWithOneUrl) {
        connector.stubGetFeatureSwitches(testServiceUrl1)(Future.failed(new Exception(s"Could not retrieve feature switches from $testServiceUrl1")))

        intercept[Exception] {
          await(service.getDownstreamFeatureSwitches)
        }
      }
    }
  }

}
