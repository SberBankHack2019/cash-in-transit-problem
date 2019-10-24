package com.sberhack.fun.app

import java.net.URI
import java.util.concurrent.atomic.AtomicBoolean

import com.sberhack.fun.struct.requests._
import com.sberhack.fun.struct.responses._
import com.typesafe.scalalogging.LazyLogging
import io.circe.Encoder
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import com.sberhack.fun.world.World

class Client(val serverUri: String) extends WebSocketClient(new URI(serverUri)) with LazyLogging {

  val isInitialized: AtomicBoolean = new AtomicBoolean(false)
  var gameConfigInit: GameConfig = _
  var pointsInit: Points = _
  var routesInit: Routes = _
  var trafficInit: Traffic = _

  override def onMessage(message: String): Unit = {
    import com.sberhack.fun.struct.json._
    var isMessageFound: Boolean = false
    logger.debug(s"Got message: $message")

    parse(message) match {
      case Left(ex) => logger.error(s"Cant parse json from server: ${ex.getMessage}", ex)
      case Right(json) =>

        if (isInitialized.get) {
          json.as[DestinationPoint] match {
            case Right(destinationPoint) =>
              logger.info("Start DestinationPoint logic!")

              World.update(destinationPoint)

              World.nextActions.foreach(car => {
                car.nextMove match {
                  case Some(move) =>
                    val goToMessage = GoTo(move.nodeId, car.name, !move.cashIn)
                    sendMessage(goToMessage)
                  case None =>
                }
              })

              return // DO NOT DELETE

            case Left(_) =>
          }

          json.as[VaultPoint] match {
            case Right(vaultPoint) =>
              logger.info("Start VaultPoint logic!")

              // todo VaultPoint LOGIC

              return // DO NOT DELETE

            case Left(_) =>
          }
        }

        if (!isMessageFound) {
          json.as[Traffic] match {
            case Right(traffic) =>
              if (!isInitialized.get) {
                if (trafficInit == null) {
                  logger.info("Init Traffic!")
                  trafficInit = traffic
                } else {
                  logger.warn("Duplicate Traffic before Init!!!")
                }
                isMessageFound = true
              } else {
                logger.info("Start Traffic logic!")

                World.updateTraffic(traffic: Traffic)

                return // DO NOT DELETE
              }

            case Left(_) =>
          }
        }

        if (!isMessageFound) {
          json.as[Points] match {
            case Right(points) =>
              if (!isInitialized.get) {
                if (pointsInit == null) {
                  logger.info("Init Points!")
                  pointsInit = points
                } else {
                  logger.warn("Duplicate Points before Init!!!")
                }
                isMessageFound = true
              } else {
                logger.info("Start Points logic!")

                // todo: Points LOGIC

                return // DO NOT DELETE
              }
            case Left(_) =>
          }
        }

        if (!isInitialized.get) {

          if (!isMessageFound) {
            json.as[Routes] match {
              case Right(routes) =>
                if (routesInit == null) {
                  logger.info("Init Routes!")
                  routesInit = routes
                } else {
                  logger.warn("Duplicate Routes before Init!!!")
                }
                isMessageFound = true
              case Left(_) =>
            }
          }

          if (!isMessageFound) {
            json.as[GameConfig] match {
              case Right(gameConfig) =>
                if (gameConfigInit == null) {
                  logger.info("Init GameConfig!")
                  gameConfigInit = gameConfig
                } else {
                  logger.warn("Duplicate GameConfig before Init!!!")
                }
                isMessageFound = true
              case Left(_) =>
            }
          }

          if (gameConfigInit != null && routesInit != null && pointsInit != null && trafficInit != null) {
            logger.info("Creating world...")

            World.create(gameConfigInit, pointsInit, routesInit, trafficInit)

            logger.info("World created!!!")
            isInitialized.set(true)
          }

        }

        if (!isMessageFound) {
          logger.error(s"Message: $message not found!!!")
        }
    }
  }

  override def onError(ex: Exception): Unit = {
    import com.sberhack.fun.struct.json._
    logger.error(ex.getMessage, ex)

    if (gameConfigInit != null) {
      logger.info("Trying to reconnect...")
      sendMessage(Reconnect(gameConfigInit.token))
    } else {
      logger.error("#########################################")
      logger.error("Cannot reconnect due to empty gameConfig!")
      System.exit(-1) // Хз че делать, когда мы еще не получили токен, но коннекшн отвалился
    }
  }

  override def onOpen(handshakedata: ServerHandshake): Unit = {
    logger.info("Connection open!")
  }

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
    logger.info("Connection closed!")
  }

  def sendMessage[T](message: T)(implicit encoder: Encoder[T]): Unit = {
    import com.sberhack.fun.struct.json._
    send(message.asJson.noSpaces)
    logger.info(s"Message $message sent to server!")
  }
}

object Client {
  def apply(serverUri: String): Client = new Client(serverUri)
}