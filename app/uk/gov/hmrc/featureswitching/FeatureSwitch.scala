package uk.gov.hmrc.featureswitching

import play.api.libs.json.{Format, Json}

trait FeatureSwitch {
  val name: String
  val displayName: String
}
