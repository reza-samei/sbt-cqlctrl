package me.samei.buildtool.cqlctrl.v1

import java.net.{InetSocketAddress}
import sbt.util.Logger
import com.datastax.driver.{core => cql}

class Func(
    name: String, logger: Logger,
    host: String, port: Int
) { self =>

    protected def mkSession(host: String, port: Int) = {
        logger debug s"${name}, Trying to connect: ${host}:${port}"
        val addr = new InetSocketAddress(host, port)
        val cluster = cql.Cluster.builder.addContactPointsWithPorts(addr).build
        val session = cluster.newSession
        logger info s"${name}, Connected: ${host}:${port}"
        session
    }

    protected def runSt(raw: String)(implicit session: cql.Session) = {
        logger debug s"${name}, Executing: ${raw}"
        val rsl = session execute raw
        // @todo log result in debug level
        logger debug s"${name}, Executed: ${raw}"
    }

    protected def runAll(list: Seq[String])(implicit session: cql.Session) = {
        logger debug s"${name}, RunAll: ${list.size}, ..."
        list foreach runSt
        logger info s"${name}, RunAll: ${list.size}, Done"
    }

    def apply(stmts: Seq[String]): Unit = stmts match {
        case Nil =>
            logger info s"${name}, No Statement to Run!"
        case list =>
            runAll(stmts)(mkSession(host, port))
    }

    override def toString = s"${getClass.getName}(${name}, ${host}:${port})"

}
