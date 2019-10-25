package com.sberhack.fun.app

object App {

  //val serverAddress = "http://localhost:8080/race"
  val serverAddress = "http://172.30.9.50:3000/race"

  def main(args: Array[String]): Unit = {
    val client = Client(serverAddress)
    client.connect()

    // logic
  }
}
