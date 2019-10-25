package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

case class StartData(
                      id: Int,
                      cars: mutable.Seq[Car]
                    ) {
  override def toString: String =
    s"id=$id, cars=${cars.mkString(", ")}"
}
