package com.sberhack.fun.app

import com.sberhack.fun.struct.json._

object App {

  val serverAddress = "ws://localhost:1337"

  def main(args: Array[String]): Unit = {
    val client = Client(serverAddress)
    client.connect()

    // logic
    client.sendMessage("Hi there")
  }
}
