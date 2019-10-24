package com.sberhack.fun.graph.node

class Point(data: PointData) extends BankNode {
  override def vertexText: String = s"< POINT $data >"

  def getData: PointData = data
}

object Point {
  def apply(data: PointData): Point = new Point(data)
}
