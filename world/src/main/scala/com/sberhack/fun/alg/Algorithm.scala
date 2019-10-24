package com.sberhack.fun.alg

import com.sberhack.fun.car.Car
import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.world.structure.World
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

  def findBestPath(paths: Seq[Path], function: Path => Double) = {
    paths.map(q => (q.head, function(q))).minBy { case (firstNodeInPath, pathValue) =>
      (pathValue, firstNodeInPath.toString)
    }._1
  }

  def calculateNextActions(world: World): Seq[Car] = {

    world.carsWithoutTask match {
      case Seq() => Seq() // Нет машин которым нечего делать
      case cars => cars.map{ car =>
       // world.world.nodes.map(_.toOuter).filter(s => )
         //genPossibleRoutes(world.world, car.currentNodeId)
        ???
      }
    }

  }

}