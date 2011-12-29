import org.agilereview.*
includeTargets << grailsScript("_GrailsBootstrap")

target(main: "Uploads survey results from a text file") {
    // TODO: Implement script here
	depends( packageApp, classpath, loadApp, configureApp )

}

setDefaultTarget(main)
