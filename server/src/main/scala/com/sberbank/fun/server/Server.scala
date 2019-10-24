package com.sberbank.fun.server

import java.net.InetSocketAddress

import com.typesafe.scalalogging.LazyLogging
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

class Server(val host: String, port: Int) extends WebSocketServer(new InetSocketAddress(host, port)) with LazyLogging {

  override def onMessage(conn: WebSocket, message: String): Unit = {
    logger.info(s"Got message: $message")

    conn.send("{ \"token\" : \"12321\", \"cars\": [\"sb1\", \"sb2\", \"sb3\", \"sb4\"], \"level\": 1 }")
  }

  override def onStart(): Unit = {
    logger.info("Server started!")
  }

  override def onError(conn: WebSocket, ex: Exception): Unit = {
    logger.error(ex.getMessage, ex)
  }

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    logger.info("Connection open!")
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    logger.info("Connection closed!")
  }
}

object Server {
  def apply(host: String, port: Int): Server = new Server(host, port)
}