package uk.gov.hmrc.featureswitching

import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

trait FeatureSwitchRegistry {

  def switches: Set[FeatureSwitch]

  def apply(str: String): FeatureSwitch =
    switches find (_.name == str) match {
      case Some(switch) => switch
      case None => throw new IllegalArgumentException("Invalid feature switch: " + str)
    }

  def get(str: String): Option[FeatureSwitch] = switches find (_.name == str)

}
