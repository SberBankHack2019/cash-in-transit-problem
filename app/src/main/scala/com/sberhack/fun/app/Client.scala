package com.sberhack.fun.app

import java.net.URI

import com.typesafe.scalalogging.LazyLogging
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import com.sberhack.fun.struct.json._
import com.sberhack.fun.struct.responses._
import io.circe.Encoder

class Client(val serverUri: String) extends WebSocketClient(new URI(serverUri)) with LazyLogging {

  override def onMessage(message: String): Unit = {
    logger.debug(s"Got message: $message")

    parse(message) match {
      case Left(ex) => logger.error(s"Cant parse json from server: ${ex.getMessage}", ex)
      case Right(json) =>

        json.as[DestinationPoint] match {
          case Right(destinationPoint) =>
            logger.info("Start DestinationPoint logic!")
            // logic
            return

          case Left(_) =>
        }

        json.as[TrafficJam] match {
          case Right(trafficJam) =>
            logger.info("Start TrafficJam logic!")
            // logic
            return

          case Left(_) =>
        }

        json.as[Points] match {
          case Right(points) =>
            logger.info("Start Points logic!")
            // logic
            return

          case Left(_) =>
        }

        json.as[Routes] match {
          case Right(routes) =>
            logger.info("Start Routes logic!")
            // logic
            return

          case Left(_) =>
        }

        json.as[Traffic] match {
          case Right(traffic) =>
            logger.info("Start Traffic logic!")
            // logic
            return

          case Left(_) =>
        }

        json.as[GameConfig] match {
          case Right(gameConfig) =>
            logger.info("Start GameConfig logic!")
            // logic
            return

          case Left(_) =>
        }

        logger.error(s"Message: $message not found!!!")
    }
  }

  override def onError(ex: Exception): Unit = {
    logger.error(ex.getMessage, ex)
  }

  override def onOpen(handshakedata: ServerHandshake): Unit = {
    logger.info("Connection open!")
  }

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
    logger.info("Connection closed!")
  }

  def sendMessage[T](message: T)(implicit encoder: Encoder[T]): Unit = {
    send(message.asJson.noSpaces)
    logger.debug(s"Message $message sent to server!")
  }
}

object Client {
  def apply(serverUri: String): Client = new Client(serverUri)
}