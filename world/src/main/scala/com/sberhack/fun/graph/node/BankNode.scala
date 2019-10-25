package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

trait BankNode{

  /* Текст в вершинах графа
   * Надо использовать метод toString потому что любой другой будет стерт в edgeTransformer
   * Потому что тот принимает WUnDiEdge без типа
   * */
  override def toString: String = vertexText

  protected def vertexText: String

  val id: Int

  var cars: mutable.Seq[Car]

  def addCar(car: Car): Unit = {
    cars = cars :+ car
  }

  def delCar(car: Car): Unit = {
    cars = cars.filterNot(_ == car)
  }

}
