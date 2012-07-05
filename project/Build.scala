import sbt._
import Keys._

object ApplicationDependencies {
  lazy val ScalaVer = "2.9.2"
}

object BuildSettings {
  import ApplicationDependencies._
  lazy val buildSettings = Defaults.defaultSettings ++ Seq (
    organization        := "com.eligotech",
    version             := "0.1-SNAPSHOT",
    scalaVersion        := ScalaVer,
    scalacOptions       := Seq("-unchecked", "-deprecation")
  )
}

object Resolvers {
  lazy val typesafeReleases = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  //lazy val scalaToolsRepo = "sonatype-oss-public" at "https://oss.sonatype.org/content/groups/public/"
}

object ApplicationBuild extends Build {
  import ApplicationDependencies._
  import Resolvers._
  import BuildSettings._

  val appDependencies = Seq(
    "org.scala-lang"  % "scala-compiler"  % ScalaVer,
    "org.specs2" %% "specs2"           % "1.11"   % "test" withSources(),
    "junit"                    % "junit"           % "4.8.2"  % "test" withSources(),
    "com.novocode"             % "junit-interface" % "0.7"    % "test"
  )

  
 lazy val algorithms = Project(
    "linter",
    file("."),
    settings =  buildSettings ++ 
                Seq(resolvers += typesafeReleases) ++  
                Seq(libraryDependencies ++= appDependencies) ++
                Seq(scalacOptions in console in Compile <+= (packageBin in Compile) map { pluginJar =>"-Xplugin:"+pluginJar }) 
  )
}
