package uk.gov.hmrc.featureswitching.testonly.config

import uk.gov.hmrc.featureswitching.FeatureSwitch

object TestFeatureSwitches {

  case object TestFs1 extends FeatureSwitch {
    override val name: String = "test-fs-one"
    override val displayName: String = "Test Feature switch 1"
  }

  case object TestFs2 extends FeatureSwitch {
    override val name: String = "test-fs-two"
    override val displayName: String = "Test Feature switch 2"
  }

  case object TestFs3 extends FeatureSwitch {
    override val name: String = "test-fs-three"
    override val displayName: String = "Test Feature switch 3"
  }

}
