package com.sberhack.fun.alg

import com.sberhack.fun.car.{Car, CarNextMove}
import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.world.structure.World
import com.sberhack.fun.alg.functions._
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge


object Algorithm  {

  type Path = Seq[BankNode]

  def genPossibleRoutes(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode): Seq[(BankNode, Path)] = {

    val nodes = graph.nodes.map(_.toOuter).filterNot(_ == currentNode)

    nodes.map { b =>
      b -> nodes.filterNot(_ == b).map { q => Seq(b, q) }.toSeq
    }.flatMap { case (n, seq) =>
      seq.map { e => (n, e) }
    }.toSeq
  }

  def findBestPath(paths: Seq[Path], function: Path => Double): BankNode = {
    paths.map(q => (q.head, function(q))).minBy { case (firstNodeInPath, pathValue) =>
      (pathValue, firstNodeInPath.toString)
    }._1
  }

  def getNodeById(world: World, nodeId: Int): BankNode = {
    world.world.nodes.map(_.toOuter).find(_.id == nodeId) match {
      case Some(value) =>  value
      case None => throw new Exception(s"Node with id $nodeId not found in graph")
    }
  }

  def calculateNextActions(world: World): Seq[Car] = {

    world.carsWithoutTask match {
      case Seq() => Seq() // Нет машин которым нечего делать
      case cars => cars.map{ car =>
       val goTo =  findBestPath(genPossibleRoutes(world.world, getNodeById(world, car.currentNodeId)).map(_._2), SergeyFunction)
        car.copy(nextMove = Some(CarNextMove(goTo.id, cashIn = true)))
      }

    }

  }

}