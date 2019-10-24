package com.sberhack.fun.app

object App {

  val serverAddress = "ws://localhost:1337"

  def main(args: Array[String]): Unit = {
    val client = Client(serverAddress)
    client.connect()

    // logic
  }
}
