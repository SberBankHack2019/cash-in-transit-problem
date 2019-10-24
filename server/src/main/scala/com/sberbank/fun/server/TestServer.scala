package com.sberbank.fun.server

import com.typesafe.scalalogging.LazyLogging

object TestServer extends LazyLogging {

  val host = "localhost"
  val port = 1337

  def main(args: Array[String]): Unit = {
    val server = Server(host, port)
    server.run()
  }
}
