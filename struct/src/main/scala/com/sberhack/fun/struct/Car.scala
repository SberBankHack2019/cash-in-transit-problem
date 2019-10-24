package com.sberhack.fun.struct

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
