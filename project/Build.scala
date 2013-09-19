import sbt._
import Keys._

object B extends Build {
  lazy val root =
    Project("root", file("."))
    .settings(
      testOptions in Test := Seq(Tests.Filter(_ == "S"))
    )
}
