package uk.gov.hmrc.featureswitching.testonly.controllers

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, AnyContent, InjectedController}
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.{FeatureSwitch, FeatureSwitchRegistry, FeatureSwitching}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BackendFeatureSwitchController @Inject()(config: Configuration,
                                               fsr: FeatureSwitchRegistry)
                                              (implicit ec: ExecutionContext) extends InjectedController with FeatureSwitching {

  private val appNameConfigKey = "appName"

  def getFeatureSwitches(implicit writes: Writes[FeatureSwitchSetting]): Action[AnyContent] = Action.async { implicit request =>
    config.getOptional[String](appNameConfigKey) match {
      case Some(appName) =>
        val switchSettings = fsr.switches.map(switch => FeatureSwitchSetting(switch.name, switch.displayName, isEnabled(switch)))
        Future.successful(Ok(Json.obj(appName -> Json.toJson(switchSettings))))
      case _ =>
        Future.successful(InternalServerError("Key appName does not exist in config"))
    }
  }

}

