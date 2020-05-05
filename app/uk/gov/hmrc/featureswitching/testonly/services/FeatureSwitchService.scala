package uk.gov.hmrc.featureswitching.testonly.services

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.Reads
import uk.gov.hmrc.featureswitching.FeatureSwitch
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FeatureSwitchService @Inject()(config: Configuration,
                                     fsrService: FeatureSwitchRegistryService,
                                     downstreamFsService: DownstreamFeatureSwitchService)
                                    (implicit ec: ExecutionContext) {

  def getFeatureSwitches(implicit reads: Reads[FeatureSwitch]): Future[FeatureSwitchSettingMap] =
    for {
      registrySwitches <- fsrService.switches
      downstreamSwitches <- downstreamFsService.getDownstreamFeatureSwitches
    } yield registrySwitches ++ downstreamSwitches

}
