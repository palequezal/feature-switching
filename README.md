# Feature Switching

Library that enables a convenient way to enable or disable features in a microservice.

## Configuration

1. Import the library in build.sbt

        libraryDependencies += "uk.gov.hmrc" %% "feature-switching" % "0.x.x"
 
2. Create a class that extends play.api.inject.Module with FeatureSwitchRegistry and register your feature switches

         @Singleton
         class YourRegister extends Module with FeatureSwitchRegistry {
         
           val switches = Set(FeatureSwitch1, FeatureSwitch2)
         
           override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
             Seq(
               bind[FeatureSwitchRegistry].to(this).eagerly()
             )
           }
         }

3. Enable the module you just created

        play.modules.enabled += uk.gov.hmrc.<YOUR PACKAGE>.YourRegister
    
4. If a backend service, enable a route to manage your feature switches
    
        GET /test-only/feature-switches  uk.gov.hmrc.featureswitching.testonly.controllers.BackendFeatureSwitchController.getFeatureSwitches
        
    If a frontend service, add a route to display feature switches from all your services
   
        GET /test-only/feature-switches  uk.gov.hmrc.featureswitching.testonly.controllers.FrontendFeatureSwitchController.show
        
    Then add the list of urls for all the downstream services that should be displayed
    
        (in application.conf) feature-switches.services = "/url1, /url2"
