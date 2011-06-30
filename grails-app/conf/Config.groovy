
/**
 * Running externalized configuration
 * Assuming the following configuration files
 * - in the executing user's home at ~/.grails/<app_name>Config/[Config.groovy|DataSource.groovy]
 * - config location set path by system variable '<APP_NAME>_CONFIG_LOCATION'
 * - dataSource location set path by system environment variable '<APP_NAME>_DATASOURCE_LOCATION'
 */
grails.config.locations = []
def defaultConfigFiles = ["${userHome}/.grails/${appName}Config/Config.groovy",
  "${userHome}/.grails/${appName}Config/DataSource.groovy"]
defaultConfigFiles.each { filePath ->
  def f = new File(filePath)
  if (f.exists()) {
    grails.config.locations << "file:${filePath}"
  } else {
  }
}
String bashSafeEnvAppName = appName.toUpperCase(Locale.ENGLISH).replaceAll(/-/, '_')

def externalConfig = System.getenv("${bashSafeEnvAppName}_CONFIG_LOCATION")
if (externalConfig) {
  grails.config.locations << "file:" + externalConfig
}
def externalDataSource = System.getenv("${bashSafeEnvAppName}_DATASOURCE_LOCATION")
if (externalDataSource) {
  grails.config.locations << "file:" + externalDataSource
}
grails.config.locations.each {
  println "[INFO] Including configuration file [${it}] in configuration building."
}
