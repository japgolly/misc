name := "Misc"

scalaVersion := "2.12.7"

scalacOptions := List(
  "-unchecked",
  "-deprecation",
  "-Xsource:2.13",
  "-Ypartial-unification",
  "-Ypatmat-exhaust-depth", "off",
  "-Ywarn-inaccessible",
  "-feature", "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

//libraryDependencies += "org.graalvm.js" % "js" % "1.0.0-rc8"

libraryDependencies += "org.graalvm.sdk" % "graal-sdk" % "1.0.0-rc8"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")

fork in run := true
