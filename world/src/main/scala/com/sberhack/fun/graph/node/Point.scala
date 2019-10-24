package com.sberhack.fun.graph.node

class Point(data: PointData) extends BankNode {
  override def vertexText: String = s"Point data: $data"
}

object Point {
  def apply(data: PointData): Point = new Point(data)
}
