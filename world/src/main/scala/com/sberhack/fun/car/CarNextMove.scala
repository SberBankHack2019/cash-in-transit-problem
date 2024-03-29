package com.sberhack.fun.car

case class CarNextMove(
                        nodeId: Int,
                        cashIn: Boolean
                      ) {
  override def toString: String = if (cashIn) {
    s"Move to Node $nodeId for cash in"
  } else {
    s"Move to Node $nodeId "
  }
}
