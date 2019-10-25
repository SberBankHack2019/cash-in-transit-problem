package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

case class PointConfig() {
  val timeRemainDefault: Double = 8.0 * 60.0
}

case class PointData (
                       id: Int,
                       money: Double,
                       carCashIn: Option[Car],
                       cars: mutable.Seq[Car],
                       timeRemain: Double
                     ) {
  override def toString: String =
    s"id=$id, money=$money, carCashIn=$carCashIn, cars=${cars.mkString(", ")}, timeRemain=$timeRemain"
}
