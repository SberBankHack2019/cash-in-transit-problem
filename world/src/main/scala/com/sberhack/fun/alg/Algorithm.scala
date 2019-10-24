package com.sberhack.fun.alg

import com.sberhack.fun.car.{Car, CarNextMove}
import com.sberhack.fun.graph.node.{BankNode, Point, Start, Vault}
import com.sberhack.fun.world.structure.World
import com.sberhack.fun.alg.functions._
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge


object Algorithm  {

  type Path = Seq[BankNode]
  type WeightedPath = Seq[(BankNode, BankNode, Double)]

  private[alg] def genPossibleRoutes(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode): Seq[(BankNode, Path)] = {

    val nodes = graph.nodes.map(_.toOuter).filterNot(_ == currentNode)

    nodes.map { b =>
      b -> nodes.filterNot(_ == b).map{ q => Seq(b, q) }
    }.flatMap{ case (n, seq) =>
      seq.map { e => (n, e) }
    }.toSeq
  }

  private[alg] def filterNotNode(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode): Graph[BankNode, WUnDiEdge] = {
    graph - (graph get currentNode)
  }


  private[alg] def genPossibleRoutes2(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode): Seq[WeightedPath] = {

    val edges: Seq[WUnDiEdge[BankNode]] = filterNotNode(graph, currentNode).edges.toOuter.toSeq

    edges.map { e =>
      edges.filterNot(_ == e).collect {
        case WUnDiEdge(from, to, weight) => (from, to, weight)
      }
    }
  }

  private[alg] def naiveAlgorythm(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode, cashLimit: Double): Option[(Int, Double)] = {
    val possibleWays = graph.edges.toOuter.collect{
      case WUnDiEdge(from, to, weight) if from == currentNode => (to, weight)
    }.collect{
     case (point: Point, weight) if point.getData.money < cashLimit => point.getData -> point.getData.money / weight
    }

      possibleWays match {
        case Seq() => None
        case ways =>
        val pnt =  ways.maxBy{ case (_, value) => value }._1
          Some((pnt.id, pnt.timeRemain))
      }



  }


  private[alg] def findBestPath(paths: Seq[Path], function: Path => Double): BankNode = {
    paths.map(q => (q.head, function(q))).minBy { case (firstNodeInPath, pathValue) =>
      (pathValue, firstNodeInPath.toString)
    }._1
  }

  private[alg] def getNodeById(world: World, nodeId: Int): BankNode = {
    world.world.nodes.map(_.toOuter).find(_.id == nodeId) match {
      case Some(value) =>  value
      case None => throw new Exception(s"Node with id $nodeId not found in graph")
    }
  }

  def calculateNextActions(world: World): Seq[Car] = {

    world.carsWithoutTask match {
      case Seq() => Seq() // Нет машин которым нечего делать
      case cars => cars.map{ car =>
        val currentNode = getNodeById(world, car.currentNodeId)
        val nextStepInfo = naiveAlgorythm(world.world, currentNode, car.cashLimit)

        nextStepInfo match {
          case Some((goTo, travelTime)) =>
            currentNode match {
              case point: Point if point.getData.timeRemain < travelTime =>
                car.copy(nextMove = Some(CarNextMove(1, cashIn = true)))
              case _ =>
                car.copy(nextMove = Some(CarNextMove(goTo, cashIn = true)))
            }
          case None =>
            car.copy(nextMove = Some(CarNextMove(1, cashIn = true)))
        }
      }
    }
  }
}