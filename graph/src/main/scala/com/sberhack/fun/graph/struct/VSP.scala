package com.sberhack.fun.graph.struct

case class VSP(
               cash: Double,
               pickupTime: Double
              ){

  /* Текст в вершинах графа */
  override def toString: String =
    s"""
       | Количество денег: ${cash.toLong} руб
       | Время на сбор: ${(pickupTime / 60).toLong} м ${(pickupTime % 60).toLong} c
     """.stripMargin

}
