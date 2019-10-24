package com.sberhack.fun.world.structure

import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.car.Car
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

case class World (
                   name: String,
                   level: Long,
                   world: Graph[BankNode, WUnDiEdge],
                   cars: Seq[Car]
                 ){

  def carsWithoutTask: Seq[Car] = cars.filter(_.nextMove.isDefined)

}
