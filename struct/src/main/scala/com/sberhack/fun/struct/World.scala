package com.sberhack.fun.struct

case class World (
                   name: String,
                   token: String,
                   level: Long,
                   world: Graph[BankNode, WUnDiEdge],
                   cars: Seq[Car]
                 ){

  def carsWithoutTask: Seq[Car] = cars.filter(_.nextMove.isDefined)

}
