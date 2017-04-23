scalaVersion := "2.12.2"

scalacOptions := List(
	"-unchecked",
	"-deprecation",
	"-Ypartial-unification",
	"-Ypatmat-exhaust-depth", "off",
	"-Ywarn-inaccessible",
	"-feature", "-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-language:existentials")

triggeredMessage := Watched.clearWhenTriggered

