package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

class Start(data: StartData) extends BankNode{
  override def vertexText: String = s"Start data: $data"
  override val id: Int = data.id
  override var cars: mutable.Seq[Car] = data.cars

  def getData: StartData = data
}

object Start {
  def apply(data: StartData): Start = new Start(data)
}
