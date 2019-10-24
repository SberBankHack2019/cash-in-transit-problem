package com.sberhack.fun.graph.node

trait BankNode{

  /* Текст в вершинах графа
   * Надо использовать метод toString потому что любой другой будет стерт в edgeTransformer
   * Потому что тот принимает WUnDiEdge без типа
   * */
  override def toString: String = vertexText

  protected def vertexText: String

  val id: Int

}
