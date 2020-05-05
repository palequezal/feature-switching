package uk.gov.hmrc.featureswitching

trait FeatureSwitching {

  val FEATURE_SWITCH_ON = "true"
  val FEATURE_SWITCH_OFF = "false"

  def isEnabled(featureSwitch: FeatureSwitch): Boolean =
    sys.props get featureSwitch.name contains FEATURE_SWITCH_ON

  def enable(featureSwitch: FeatureSwitch): Unit =
    sys.props += featureSwitch.name -> FEATURE_SWITCH_ON

  def disable(featureSwitch: FeatureSwitch): Unit =
    sys.props += featureSwitch.name -> FEATURE_SWITCH_OFF

}
