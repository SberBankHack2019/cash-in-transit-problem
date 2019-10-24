package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class StartData(
                      id: Int,
                      cars: Seq[Car]
                    ) {
  override def toString: String =
    s"id=$id, cars=${cars.mkString(", ")}"
}
