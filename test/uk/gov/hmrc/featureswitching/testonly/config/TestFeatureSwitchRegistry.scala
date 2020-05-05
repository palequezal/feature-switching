package uk.gov.hmrc.featureswitching.testonly.config

import javax.inject.Singleton
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}
import uk.gov.hmrc.featureswitching.FeatureSwitchRegistry
import uk.gov.hmrc.featureswitching.testonly.config.TestFeatureSwitches.{TestFs1, TestFs2}

@Singleton
class TestRegistry extends Module with FeatureSwitchRegistry {

  val switches = Set(TestFs1, TestFs2)

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[FeatureSwitchRegistry].to(this).eagerly()
    )
  }

}


