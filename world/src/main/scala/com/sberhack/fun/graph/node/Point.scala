package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

class Point(data: PointData) extends BankNode {
  override def vertexText: String = s"< POINT $data >"
  override val id: Int = data.id
  override var cars: mutable.Seq[Car] = data.cars

  def getData: PointData = data
}

object Point {
  def apply(data: PointData): Point = new Point(data)
}
