package com.sberhack.fun.graph

import com.sberhack.fun.car.Car
import com.sberhack.fun.graph.node._
import com.sberhack.fun.struct.responses.{EdgeRoute, GameConfig, Points, Routes, Traffic, VertexPoint}
import scalax.collection.Graph
import scalax.collection.edge.Implicits._
import scalax.collection.edge.WUnDiEdge

package object generator {

  def createGraph(config: GameConfig,
                  cars: Seq[Car],
                  points: Points,
                  routes: Routes,
                  traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    //create Start
    val startNode: BankNode = Start(StartData(0, cars))
    //create Vault
    val vaultNode: BankNode = Vault(VaultData(1, 0, cars))
    //create PointConfig
    val pointConfig = PointConfig()

    //create Points
    val pointNodes: Seq[BankNode] = points.points
      .map{ point: VertexPoint => point.p match {
        case 0 => startNode
        case 1 => vaultNode
        case _ => Point(PointData(point.p, point.money, None, Seq.empty[Car], pointConfig.timeRemainDefault))
        }
      }

    //create NodeEdges
    val nodeEdges: Seq[WUnDiEdge[BankNode]] = routes.routes
      .map{ route: EdgeRoute => (route.a, route.b) match {
          case (0, 0) => (startNode ~% startNode) (route.time * getJam(route, traffic))
          case (0, 1) => (startNode ~% vaultNode) (route.time * getJam(route, traffic))
          case (0, _) => (startNode ~% getNodes(route, pointNodes)._2) (route.time * getJam(route, traffic))
          case (_, 0) => (getNodes(route, pointNodes)._1 ~% startNode) (route.time * getJam(route, traffic))
          case (1, 0) => (vaultNode ~% startNode) (route.time * getJam(route, traffic))
          case (1, 1) => (vaultNode ~% vaultNode) (route.time * getJam(route, traffic))
          case (1, _) => (vaultNode ~% getNodes(route, pointNodes)._2) (route.time * getJam(route, traffic))
          case (_, 1) => (getNodes(route, pointNodes)._1 ~% vaultNode) (route.time * getJam(route, traffic))
          case (_, _) => (getNodes(route, pointNodes)._1 ~% getNodes(route, pointNodes)._2) (route.time * getJam(route, traffic))
        }
      }

    Graph.from(pointNodes, nodeEdges)
  }

  def nodeEdges(routes: Routes): Seq[WUnDiEdge[BankNode]] = {
    ???
  }

  def updateGraph(graph: Graph[BankNode, WUnDiEdge], traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    ???
  }

  def updateGraph(graph: Graph[BankNode, WUnDiEdge], cars: Seq[Car]): Graph[BankNode, WUnDiEdge] = {
    ???
  }

  def getMaybeJam(route: EdgeRoute, traffic: Traffic): Option[Double] = {
    traffic.traffic.find(edge => edge.a == route.a & edge.b == route.b).map(_.jam.toDouble)
  }

  def getJam(route: EdgeRoute, traffic: Traffic): Double = {
    getMaybeJam(route, traffic).getOrElse(1.0)
  }

  def getMaybeNodeById(id: Int, nodes: Seq[BankNode]): Option[BankNode] = {
    id match {
      case 0 => nodes.find {
        case _: Start => true
        case _ => false
      }.map(_.asInstanceOf[Start])

      case 1 => nodes.find {
        case _: Vault => true
        case _ => false
      }.map(_.asInstanceOf[Vault])

      case a => nodes.find {
        case node: Point => node.getData.id == a
        case _ => false
      }.map(_.asInstanceOf[Point])
    }
  }

  def getMaybeNodes(route: EdgeRoute, nodes: Seq[BankNode]): (Option[BankNode], Option[BankNode]) = {
    (getMaybeNodeById(route.a, nodes), getMaybeNodeById(route.b, nodes))
  }

  def getNodes(route: EdgeRoute, nodes: Seq[BankNode]): (BankNode, BankNode) = {
    getMaybeNodes(route, nodes) match {
      case (Some(n1), Some(n2)) => (n1, n2)
      case (_, _) =>
        throw new Exception(s"Points from EdgeRoute: (${route.a}, ${route.b}) Not Found in Nodes ${nodes.mkString(", ")}")
    }
  }

}
