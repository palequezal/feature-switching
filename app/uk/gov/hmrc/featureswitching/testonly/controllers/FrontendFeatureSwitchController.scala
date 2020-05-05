package uk.gov.hmrc.featureswitching.testonly.controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{InjectedController, Request}
import play.twirl.api.Html
import uk.gov.hmrc.featureswitching.{FeatureSwitch, FeatureSwitchRegistry, FeatureSwitching}

import scala.collection.immutable.ListMap
import scala.concurrent.Future

@Singleton
abstract class FrontendFeatureSwitchController @Inject()(fsr: FeatureSwitchRegistry) extends InjectedController with FeatureSwitching {

  private def view(switchNames: Map[FeatureSwitch, Boolean])(implicit request: Request[_]): Html = ???

//  def show = Action.async { implicit req =>
//    for {
//      featureSwitches <- ListMap(fsr.switches.toSeq sortBy (_.displayName) map (switch => switch -> isEnabled(switch)): _*)
//    } yield Future.successful(Ok(""))//Ok(view(featureSwitches))
//  }

//  def submit = Action.async { implicit req =>
//    val submittedData: Set[String] = req.body.asFormUrlEncoded match {
//      case None => Set.empty
//      case Some(data) => data.keySet
//    }
//
//    val frontendFeatureSwitches = submittedData flatMap fsr.get
//
//    fsr.switches.foreach(fs =>
//      if (frontendFeatureSwitches.contains(fs)) fsr.enable(fs)
//      else fsr.disable(fs)
//    )

  //}

}
