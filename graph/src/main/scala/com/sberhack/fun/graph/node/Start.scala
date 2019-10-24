package com.sberhack.fun.graph.node

class Start(data: StartData) extends BankNode{
  override def vertexText: String = s"Start data: $data"
}

object Start {
  def apply(data: StartData): Start = new Start(data)
}
