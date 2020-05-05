package uk.gov.hmrc.featureswitching.testonly.services

import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json.Reads
import uk.gov.hmrc.featureswitching.testonly.connectors.DownstreamFeatureSwitchConnector
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}

class DownstreamFeatureSwitchService @Inject()(config: Configuration,
                                               connector: DownstreamFeatureSwitchConnector)
                                              (implicit ec: ExecutionContext) {

  private val featureSwitchSeqKey: String = "feature-switches.services"
  private val separator: String = ","

  protected[services] def getDownstreamFeatureSwitches(implicit reads: Reads[FeatureSwitchSetting]): Future[FeatureSwitchSettingMap] =
    config.getOptional[String](featureSwitchSeqKey) match {
      case Some(switchStr) => {
        val getOperations = switchStr.split(separator)
          .map(_.trim)
          .toSet
          .map(url => connector.getFeatureSwitches(url))

        Future.sequence(getOperations).map(_.reduce(_ ++ _))
      }
      case _ =>
        Future.successful(Map.empty)
    }

}
