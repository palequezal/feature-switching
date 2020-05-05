package uk.gov.hmrc.featureswitching.testonly.controllers

import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration}
import play.api.test.Helpers._
import uk.gov.hmrc.featureswitching.{FeatureSwitchRegistry, FeatureSwitching}
import uk.gov.hmrc.featureswitching.testonly.BaseSpec
import uk.gov.hmrc.featureswitching.testonly.config.TestFeatureSwitches.{TestFs1, TestFs2}
import uk.gov.hmrc.featureswitching.testonly.config.TestRegistry
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap
import FeatureSwitchSetting._
import play.api.libs.json.Json
import play.api.test.FakeRequest

import scala.concurrent.ExecutionContext.Implicits.global

class BackendFeatureSwitchControllerSpec extends BaseSpec with GuiceOneAppPerSuite with FeatureSwitching {

  val testAppName = "test-app"
  val testConfig = Configuration.from(Map("appName" -> testAppName))

  override lazy val app: Application = GuiceApplicationBuilder()
    .bindings(new TestRegistry)
    .configure(testConfig)
    .build()

  class Setup() {
    val fsr = app.injector.instanceOf[FeatureSwitchRegistry]
    val controller = new BackendFeatureSwitchController(app.configuration, fsr)
    controller.setControllerComponents(stubControllerComponents())
  }

  val fsResponse: FeatureSwitchSettingMap = Map(testAppName -> Set(
    FeatureSwitchSetting(TestFs1.name, TestFs1.displayName, true),
    FeatureSwitchSetting(TestFs2.name, TestFs2.displayName, false)
  ))

  "getAllFeatureSwitches()" should {
    "return a list of feature switches" in new Setup() {
      enable(TestFs1)
      val res = controller.getFeatureSwitches.apply(FakeRequest())

      contentAsJson(res) shouldBe Json.toJson(fsResponse)
    }
  }

}
