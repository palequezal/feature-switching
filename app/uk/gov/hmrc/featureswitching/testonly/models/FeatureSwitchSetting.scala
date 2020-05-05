package uk.gov.hmrc.featureswitching.testonly.models

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.featureswitching.FeatureSwitch

case class FeatureSwitchSetting(name: String,
                                displayName: String,
                                isEnabled: Boolean)

object FeatureSwitchSetting {
  type FeatureSwitchSettingMap = Map[String, Set[FeatureSwitchSetting]]

  implicit val format: Format[FeatureSwitchSetting] = Json.format[FeatureSwitchSetting]
}
