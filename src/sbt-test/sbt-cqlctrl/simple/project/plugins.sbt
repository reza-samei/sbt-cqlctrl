
/*
sys.props.get("plugin.version") match {
    case Some(x) => addSbtPlugin("com.eed3si9n" % "sbt-assembly" % x)
    case _ => sys.error("""|The system property 'plugin.version' is not defined.
                           |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}
*/

addSbtPlugin("me.samei.buildtool" % "sbt-cqlctrl" % "0.1.0-SNAPSHOT")

