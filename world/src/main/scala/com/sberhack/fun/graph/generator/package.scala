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
    val vaultNode: BankNode = Vault(VaultData(0, 0, cars))
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
          case (1, 0) => (vaultNode ~% startNode) (route.time * getJam(route, traffic))
          case (1, 1) => (vaultNode ~% vaultNode) (route.time * getJam(route, traffic))
          case (_, _) => (getPoints(route, points)._1 ~% getPoints(route, points)._2) (route.time * getJam(route, traffic))
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

  def getMaybePoints(route: EdgeRoute, points: Points): (Option[Point], Option[Point]) = {
    val pointA: Option[Point] = points.points.find(point => point.p == route.a).map{ point =>
      val pointConfig = PointConfig()
      Point(PointData(point.p, point.money, None, Seq.empty[Car], pointConfig.timeRemainDefault))
    }
    val pointB: Option[Point] = points.points.find(point => point.p == route.b).map{ point =>
      val pointConfig = PointConfig()
      Point(PointData(point.p, point.money, None, Seq.empty[Car], pointConfig.timeRemainDefault))
    }

    (pointA, pointB)
  }

  def getPoints(route: EdgeRoute, points: Points): (Point, Point) = {
    getMaybePoints(route, points) match {
      case (Some(p1), Some(p2)) => (p1, p2)
      case (_, _) => throw new Exception(s"Not Found in Points (a, b) from EdgeRoute: (${route.a}, ${route.b})")
    }
  }

}
