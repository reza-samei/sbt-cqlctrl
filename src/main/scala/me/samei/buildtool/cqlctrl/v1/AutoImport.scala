package me.samei.buildtool.cqlctrl.v1

import sbt._

trait Val {

    def defaultCqlHost: String = "localhost"

    def defaultCqlPort: Int = 9042

    def emotySeq: Seq[String] = Seq.empty[String]

}


trait Key {

    val cqlHost = settingKey[String]("Cassandra Host")

    val cqlPort = settingKey[Int]("Cassandra Port")

    lazy val cqlStatements = taskKey[Seq[String]]("CQL Statements to run on Cassandra")

    lazy val cqlRun = taskKey[Unit]("Run CQL Statement on defined cassandra!")
}

class AutoImport extends Key with Val
