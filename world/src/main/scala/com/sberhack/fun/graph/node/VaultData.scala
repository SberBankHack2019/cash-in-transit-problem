package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class VaultData (
                       id: Int,
                       money: Double,
                       cars: Seq[Car]
                     ) {
  override def toString: String =
    s"id=$id, money=$money, cars=${cars.mkString(", ")}"
}
