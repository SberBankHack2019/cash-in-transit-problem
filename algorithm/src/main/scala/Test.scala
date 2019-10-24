import com.sberhack.fun.car.{Car, CarNextMove}

object Test extends App {
  val car = Car("sb1", 100000.0, 1000000.0, 0L, Some(CarNextMove(1, cashIn = false)))

  println(car)
}
