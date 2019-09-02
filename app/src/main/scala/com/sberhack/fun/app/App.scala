package com.sberhack.fun.app

import java.io.IOException

import zio.console._
import zio.{ZIO, App => ZIOApp}

object App extends ZIOApp {
  final def run(args: List[String]): ZIO[Console, Nothing, Int] =
    myAppLogic.fold(_ => 1, _ => 0)

    def myAppLogic: ZIO[Console, IOException, Unit] =
      for {
        _ <- putStrLn("Hello! What is your name?")
        name <- getStrLn
        _ <- putStrLn("Hello, " + name + ", good to meet you!")
      } yield ()
}
