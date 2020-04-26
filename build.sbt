scalaVersion := "2.13.2"

enablePlugins(JmhPlugin)

Compile / scalaSource := baseDirectory.value / "src"
