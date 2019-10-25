package com.sberhack.fun.app

object App {

  val serverAddress = "http://localhost:8080/race"

  def main(args: Array[String]): Unit = {
    val client = Client(serverAddress)
    client.connect()

    // logic
  }
}
