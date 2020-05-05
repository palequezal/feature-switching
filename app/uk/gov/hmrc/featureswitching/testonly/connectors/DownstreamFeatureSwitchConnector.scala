package uk.gov.hmrc.featureswitching.testonly.connectors

import javax.inject.{Inject, Singleton}
import play.api.http.Status._
import play.api.libs.json.{JsError, JsSuccess, Reads}
import play.api.libs.ws.WSClient
import uk.gov.hmrc.featureswitching.FeatureSwitch
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DownstreamFeatureSwitchConnector @Inject()(wsClient: WSClient)(implicit ec: ExecutionContext) {

  def getFeatureSwitches(url: String)(implicit reads: Reads[FeatureSwitchSettingMap]): Future[FeatureSwitchSettingMap] = {
    wsClient.url(url).get.map(response =>
      response.status match {
        case OK =>
          (response.json \ "switches").validate[FeatureSwitchSettingMap] match {
            case JsSuccess(settingMap, _) => settingMap
            case JsError(errors) => throw new Exception(errors.head.toString)
          }
        case _ => throw new Exception(s"Could not retrieve feature switches from $url")
      }
    )
  }

}
