package com.sberhack.fun.graph.vertex

trait BankBuilding{

  /* Текст в вершинах графа
   * Надо использовать метод toString потому что любой другой будет стерт в edgeTransformer
   * Потому что тот принимает WUnDiEdge без типа
   * */
  override def toString: String = vertexText

  protected def vertexText: String

}
