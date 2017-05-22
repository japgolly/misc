name := "Misc"

scalaVersion := "2.12.2"

scalacOptions := List(
	"-unchecked",
	"-deprecation",
	"-Ypartial-unification",
	"-Ypatmat-exhaust-depth", "off",
	"-Ywarn-inaccessible",
	"-feature", "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

triggeredMessage := Watched.clearWhenTriggered

