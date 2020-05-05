package uk.gov.hmrc.featureswitching.testonly.services.mocks

import org.mockito.Mockito._
import org.scalatest.Suite
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.featureswitching.FeatureSwitch
import uk.gov.hmrc.featureswitching.testonly.config.TestRegistry


object MockFeatureSwitchRegistry extends MockitoSugar {
  self: Suite =>

  val mockFeatureSwitchRegistry = mock[TestRegistry]

  def getFeatureSwitches(response: Set[FeatureSwitch]) =
    when(mockFeatureSwitchRegistry.switches) thenReturn response

}
