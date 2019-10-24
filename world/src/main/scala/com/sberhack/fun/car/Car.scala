package com.sberhack.fun.car

case class CarConfig() {
  val cashLimitDefault: Double = 1000000.0
}

case class Car(
                name: String,
                cash: Double,
                cashLimit: Double,
                currentNodeId: Long,
                nextMove: Option[CarNextMove]
              ) {

  override def toString: String =
    s"""(Car \"$name\"
       | cash: $cash
       | limit: $cashLimit
       | current Node: $currentNodeId
       | next Move: ${nextMove.getOrElse("None")}
       )""".stripMargin



}
