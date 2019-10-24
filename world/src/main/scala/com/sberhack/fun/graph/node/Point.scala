package com.sberhack.fun.graph.node

class Point(data: PointData) extends BankNode {
  override def vertexText: String = s"< POINT $data >"
  override val id: Int = data.id

  def getData: PointData = data
}

object Point {
  def apply(data: PointData): Point = new Point(data)
}
