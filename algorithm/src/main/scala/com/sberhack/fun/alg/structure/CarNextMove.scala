package com.sberhack.fun.alg.structure

case class CarNextMove(
                        nodeId: Long,
                        cashIn: Boolean
                      ) {
  override def toString: String = if (cashIn) {
    s"Move to Node $nodeId for cash in"
  } else {
    s"Move to Node $nodeId "
  }

}
