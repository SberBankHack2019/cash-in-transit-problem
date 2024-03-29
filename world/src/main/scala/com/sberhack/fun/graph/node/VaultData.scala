package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

case class VaultData (
                       id: Int,
                       money: Double, // Количество денег которые уже отвезены в хранилище
                       cars: mutable.Seq[Car]
                     ) {
  override def toString: String =
    s"id=$id, money=$money, cars=${cars.mkString(", ")}"
}
