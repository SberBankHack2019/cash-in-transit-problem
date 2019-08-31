import java.io.IOException

import zio.console._
import zio.{App, ZIO}

object App extends App {
  final def run(args: List[String]): ZIO[Console, Nothing, Int] =
    myAppLogic.fold(_ => 1, _ => 0)

    def myAppLogic: ZIO[Console, IOException, Unit] =
      for {
        _ <- putStrLn("Hello! What is your name?")
        n <- getStrLn
        _ <- putStrLn("Hello, " + n + ", good to meet you!")
      } yield ()
}
