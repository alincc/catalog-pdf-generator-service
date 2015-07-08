statusListener(OnConsoleStatusListener)
scan()

def catalinaBase = System.getProperty("catalina.base")
def catalinaHome = System.getProperty("catalina.home")

def appenderList = []
def logDir = "/tmp"
def prod = false;

if (catalinaBase != null) {
  logDir = "${catalinaBase}/logs"
} else {
  logDir = "/tmp/byggmester-bob/logs"
}

// does hostname match appserv?
if (hostname.startsWith("appserv")) {
  prod = true;
}

if (!prod) {
  appenderList.add("CONSOLE")

  appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
      pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
  }
}
appenderList.add("ROLLING")

appender("ROLLING", RollingFileAppender) {
  file = "${logDir}/byggmester-bob.log"
  rollingPolicy(TimeBasedRollingPolicy) {
    FileNamePattern = "${logDir}/byggmester-bob-%d{yyyy-MM-dd}.zip"
  }
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}

root(WARN, appenderList)

if (!prod) {
    logger("no.nb.sesam.bb", DEBUG)
}