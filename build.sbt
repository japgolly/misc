name      := "ShipReq"
startYear := Some(2013)

// Allow ctrl-c to kill forked tasks without killing SBT
cancelable in Global := true

val root                  = ShipReqBuild.root

val webappBaseTestJvm     = WebappBuild.webappBaseTestJvm
val webappBaseTestJs      = WebappBuild.webappBaseTestJs
