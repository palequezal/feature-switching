package uk.gov.hmrc.featureswitching.testonly.services

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration}
import play.api.test.Helpers._
import uk.gov.hmrc.featureswitching.testonly.BaseSpec
import uk.gov.hmrc.featureswitching.{FeatureSwitchRegistry, FeatureSwitching}
import uk.gov.hmrc.featureswitching.testonly.config.TestFeatureSwitches.{TestFs1, TestFs2}
import uk.gov.hmrc.featureswitching.testonly.config.TestRegistry
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap
import uk.gov.hmrc.featureswitching.testonly.services.mocks.MockFeatureSwitchRegistry

import scala.concurrent.ExecutionContext.Implicits.global

class FeatureSwitchRegistryServiceSpec extends BaseSpec with GuiceOneAppPerSuite with FeatureSwitching {

  override lazy val app: Application = GuiceApplicationBuilder()
    .bindings(new TestRegistry)
    .build()

  class Setup(config: Configuration = Configuration()) {
    val service = new FeatureSwitchRegistryService(config, MockFeatureSwitchRegistry.mockFeatureSwitchRegistry)
  }

  val fsResponse: FeatureSwitchSettingMap = Map("registry" -> Set(
    FeatureSwitchSetting(TestFs1.name, TestFs1.displayName, true),
    FeatureSwitchSetting(TestFs2.name, TestFs2.displayName, false)
  ))

  "switches" when {
    "the registry contains feature switches" should {
      "return a feature switch map" in new Setup() {
        enable(TestFs1)
        MockFeatureSwitchRegistry.getFeatureSwitches(Set(TestFs1, TestFs2))

        val result = await(service.switches)

        result shouldBe fsResponse
      }
    }
    "attempting to inject a FeatureSwitchRegistry" should {
      "return the feature switches from the bound module" in {
        enable(TestFs1)
        val fsr = app.injector.instanceOf[FeatureSwitchRegistry]
        val service = new FeatureSwitchRegistryService(
          config = Configuration.from(
            Map("play.modules.enabled" -> "uk.gov.hmrc.featureswitching.testonly.services.mocks.TestRegistry")
          ),
          featureSwitchRegistry = fsr
        )

        val result = await(service.switches)

        result shouldBe fsResponse
      }
    }
  }

}
