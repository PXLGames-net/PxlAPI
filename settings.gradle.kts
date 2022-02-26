rootProject.name = "PxlAPI"

//Repositorys for Plugins
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}
include("px-core")
include("pxl-api")
include("pxl-proxy")
include("pxl-server")
include("pxl-core")
