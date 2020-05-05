package uk.gov.hmrc.featureswitching.testonly.connectors.mocks

import org.mockito.ArgumentMatchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.Suite
import uk.gov.hmrc.featureswitching.testonly.connectors.DownstreamFeatureSwitchConnector
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.concurrent.Future

object MockDownstreamFeatureswitchConnector extends MockitoSugar {
  self: Suite =>

  val mockDownstreamFsConnector = mock[DownstreamFeatureSwitchConnector]

  def stubGetFeatureSwitches(url: String)(response: Future[FeatureSwitchSettingMap]) =
    when(mockDownstreamFsConnector.getFeatureSwitches(ArgumentMatchers.eq(url))(ArgumentMatchers.any())) thenReturn response

}
