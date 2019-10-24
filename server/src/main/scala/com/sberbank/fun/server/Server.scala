package com.sberbank.fun.server

import java.net.InetSocketAddress

import com.sberhack.fun.struct.responses._
import com.sberhack.fun.struct.json._
import com.typesafe.scalalogging.LazyLogging
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

class Server(val host: String, port: Int) extends WebSocketServer(new InetSocketAddress(host, port)) with LazyLogging {

  override def onMessage(conn: WebSocket, message: String): Unit = {
    logger.info(s"Got message: $message")
  }

  override def onStart(): Unit = {
    logger.info("Server started!")
  }

  override def onError(conn: WebSocket, ex: Exception): Unit = {
    logger.error(ex.getMessage, ex)
  }

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    logger.info("Connection open!")

    // init
    conn.send(Traffic(List(EdgeTraffic(1, 2, 10))).asJson.noSpaces)
    Thread.sleep(1000)
    conn.send(GameConfig("token", List("car1", "car2"), 1).asJson.noSpaces)
    Thread.sleep(1000)
    conn.send(Points(List(VertexPoint(1, 10000))).asJson.noSpaces)
    Thread.sleep(1000)
    conn.send(Routes(List(Route(1, 2, 15))).asJson.noSpaces)
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    logger.info("Connection closed!")
  }
}

object Server {
  def apply(host: String, port: Int): Server = new Server(host, port)
}