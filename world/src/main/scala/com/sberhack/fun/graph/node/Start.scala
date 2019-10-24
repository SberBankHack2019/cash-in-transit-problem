package com.sberhack.fun.graph.node

class Start(data: StartData) extends BankNode{
  override def vertexText: String = s"< START $data >"

  def getData: StartData = data
}

object Start {
  def apply(data: StartData): Start = new Start(data)
}
