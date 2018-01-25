package me.samei.buildtool.cqlctrl.v1

import sbt._
import sbt.Keys._

class Plugin extends AutoPlugin {

    override def trigger = allRequirements

    override def requires = sbt.plugins.JvmPlugin

    object autoImport extends AutoImport

    import autoImport._

    override def projectSettings = Seq(

        cqlHost in sbt.Test := defaultCqlHost,
        cqlPort in sbt.Test := defaultCqlPort,
        cqlStatements in sbt.Test := emotySeq,

        cqlRun in sbt.Test := {

            val logger: sbt.util.Logger = streams.value.log("CqlRun")
            val stmts = (Test / cqlStatements).value

            new Func(
                "CqlRun",
                logger,
                (Test / cqlHost).value,
                (Test / cqlPort).value
            ) apply stmts
        },

        test in Test := {
            (Test / cqlRun).value
            (Test / test).value
        }

        // test in Test := Def sequential (cqlRun in Test, test in Test)
        // cqlRun := { cqlRun triggeredBy (test in Test) }
        // Overrided main
    )

}

object Plugin extends Plugin
