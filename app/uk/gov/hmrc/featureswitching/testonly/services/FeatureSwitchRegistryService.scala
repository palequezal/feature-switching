package uk.gov.hmrc.featureswitching.testonly.services

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.featureswitching.{FeatureSwitchRegistry, FeatureSwitching}
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting
import uk.gov.hmrc.featureswitching.testonly.models.FeatureSwitchSetting.FeatureSwitchSettingMap

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FeatureSwitchRegistryService @Inject()(config: Configuration,
                                             val featureSwitchRegistry: FeatureSwitchRegistry)
                                            (implicit ec: ExecutionContext) extends FeatureSwitching {

  protected[services] def switches: Future[FeatureSwitchSettingMap] = {
    val switches = Map("registry" -> featureSwitchRegistry.switches
      .map(fs => FeatureSwitchSetting(fs.name, fs.displayName, isEnabled(fs)))
    )

    Future.successful(switches)
  }

}
