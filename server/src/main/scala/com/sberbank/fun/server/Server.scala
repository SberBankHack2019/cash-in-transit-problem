package com.sberbank.fun.server

import java.net.InetSocketAddress

import com.typesafe.scalalogging.LazyLogging
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

import scala.io.Source

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

    val traffic = Source.fromFile("server\\src\\main\\scala\\com\\sberbank\\fun\\server\\json\\trafficInit.json")
      .bufferedReader.readLine
    val gameConfig = Source.fromFile("server\\src\\main\\scala\\com\\sberbank\\fun\\server\\json\\gameConfig.json")
      .bufferedReader.readLine
    val points = Source.fromFile("server\\src\\main\\scala\\com\\sberbank\\fun\\server\\json\\points.json")
      .bufferedReader.readLine
    val routes = Source.fromFile("server\\src\\main\\scala\\com\\sberbank\\fun\\server\\json\\routes.json")
      .bufferedReader.readLine

    // init
    conn.send(traffic)
    //Thread.sleep(1000)
    conn.send(gameConfig)
    //Thread.sleep(1000)
    conn.send(points)
    //Thread.sleep(1000)
    conn.send(routes)
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    logger.info("Connection closed!")
  }
}

object Server {
  def apply(host: String, port: Int): Server = new Server(host, port)
}