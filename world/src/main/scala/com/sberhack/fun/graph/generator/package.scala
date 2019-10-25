package com.sberhack.fun.graph

import com.sberhack.fun.car.Car
import com.sberhack.fun.graph.node._
import com.sberhack.fun.struct.responses.{EdgeRoute, GameConfig, Points, Routes, Traffic, VertexPoint}
import scalax.collection.Graph
import scalax.collection.edge.Implicits._
import scalax.collection.edge.WUnDiEdge

import scala.collection.mutable

package object generator {

  def createGraph(config: GameConfig,
                  cars: mutable.Seq[Car],
                  points: Points,
                  routes: Routes,
                  traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    //create Nodes
    val pointNodes: Seq[BankNode] =
      createPointNodes(cars, PointConfig(), points)

    //create Edges
    val nodeEdges: Seq[WUnDiEdge[BankNode]] =
      createNodeEdges(cars, routes, traffic, pointNodes)

    Graph.from(pointNodes, nodeEdges)
  }

  def createStartNode(cars: mutable.Seq[Car]): Start = Start(StartData(0, cars))
  def createVaultNode(cars: mutable.Seq[Car]): Vault = Vault(VaultData(1, 0, cars))

  def createPointNode(pointConfig: PointConfig, point: VertexPoint): Point =
    Point(PointData(point.p, point.money, None, mutable.Seq.empty[Car], pointConfig.timeRemainDefault))

  def createPointNodes(cars: mutable.Seq[Car], pointConfig: PointConfig, points: Points): Seq[BankNode] =
    points.points
      .map{ point: VertexPoint => point.p match {
          case 0 => createStartNode(cars)
          case 1 => createVaultNode(cars)
          case _ => createPointNode(pointConfig, point)
        }
      }

  def createNodeEdges(cars: mutable.Seq[Car],
                      routes: Routes,
                      traffic: Traffic,
                      nodes: Seq[BankNode]): Seq[WUnDiEdge[BankNode]] = {
    val startNode: BankNode = createStartNode(cars)
    val vaultNode: BankNode = createVaultNode(cars)

    routes.routes
      .map { route: EdgeRoute =>
        (route.a, route.b) match {
          case (0, 0) => (startNode ~% startNode) (route.time * getJam(route, traffic))
          case (0, 1) => (startNode ~% vaultNode) (route.time * getJam(route, traffic))
          case (0, _) => (startNode ~% getNodes(route, nodes)._2) (route.time * getJam(route, traffic))
          case (_, 0) => (getNodes(route, nodes)._1 ~% startNode) (route.time * getJam(route, traffic))
          case (1, 0) => (vaultNode ~% startNode) (route.time * getJam(route, traffic))
          case (1, 1) => (vaultNode ~% vaultNode) (route.time * getJam(route, traffic))
          case (1, _) => (vaultNode ~% getNodes(route, nodes)._2) (route.time * getJam(route, traffic))
          case (_, 1) => (getNodes(route, nodes)._1 ~% vaultNode) (route.time * getJam(route, traffic))
          case (_, _) => (getNodes(route, nodes)._1 ~% getNodes(route, nodes)._2) (route.time * getJam(route, traffic))
        }
      }
  }

  def updateGraph(graph: Graph[BankNode, WUnDiEdge], car: Car): Graph[BankNode, WUnDiEdge] = {
    //update Nodes
    val pointNodes: Seq[BankNode] = graph.nodes.toOuter.toSeq
    val pointNodesUpdated = updatePointNodes(pointNodes, car)

    //update Edges
    val nodeEdges: Seq[WUnDiEdge[BankNode]] = graph.edges.toOuter.toSeq
    val nodeEdgesUpdated = updateNodeEdges(pointNodes, nodeEdges, car)

    Graph.from(pointNodesUpdated, nodeEdgesUpdated)
  }

  def updateGraph(graph: Graph[BankNode, WUnDiEdge],
                  routes: Routes,
                  traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    //get Nodes
    val pointNodes: Seq[BankNode] = graph.nodes.toOuter.toSeq

    //update Edges
    val nodeEdges: Seq[WUnDiEdge[BankNode]] = graph.edges.toOuter.toSeq
    val nodeEdgesUpdated = updateNodeEdges(pointNodes, nodeEdges, routes, traffic)

    Graph.from(pointNodes, nodeEdgesUpdated)
  }

  def addStartNodeById(nodes: Seq[BankNode], car: Car): Start = {
    val start = findNodeById(nodes, 0).get.asInstanceOf[Start]
    addCarToNode(start, car).asInstanceOf[Start]
  }

  def addVaultNodeById(nodes: Seq[BankNode], car: Car): Vault = {
    val vault = findNodeById(nodes, 1).get.asInstanceOf[Vault]
    val vaultNew = addCarToNode(vault, car).asInstanceOf[Vault]
    Vault(vaultNew.getData.copy(
      vaultNew.getData.id, vault.getData.money + car.cash, vault.getData.cars
    ))
  }

  def addPointNodeById(id: Int, nodes: Seq[BankNode], car: Car): Point = {
    val point = findNodeById(nodes, id).get.asInstanceOf[Point]
    val pointNew = addCarToNode(point, car).asInstanceOf[Point]
    car.nextMove match {
      case Some(move) if move.cashIn =>
        Point(pointNew.getData.copy(
          pointNew.getData.id, 0, Some(car), pointNew.getData.cars, pointNew.getData.timeRemain
        ))
      case _ => pointNew
    }
  }

  def updatePointNodes(nodes: Seq[BankNode], car: Car): Seq[BankNode] = {
    car.currentNodeId match {
      case 0 =>
        val start = findNodeById(nodes, 0).get.asInstanceOf[Start]
        val startNew = addStartNodeById(nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        nodes.filterNot(Seq(start, node).contains(_)) ++ Seq(startNew, nodeNew)
      case 1 =>
        val vault = findNodeById(nodes, 1).get.asInstanceOf[Vault]
        val vaultNewSum = addVaultNodeById(nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        nodes.filterNot(Seq(vault, node).contains(_)) ++ Seq(vaultNewSum, nodeNew)
      case i =>
        val point = findNodeById(nodes, i).get.asInstanceOf[Point]
        val pointNewCash = addPointNodeById(i, nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        nodes.filterNot(Seq(point, node).contains(_)) ++ Seq(pointNewCash, nodeNew)
    }
  }

  def updateNodeEdges(nodes: Seq[BankNode],
                      edges: Seq[WUnDiEdge[BankNode]],
                      routes: Routes,
                      traffic: Traffic): Seq[WUnDiEdge[BankNode]] = {
    edges.map { edge: WUnDiEdge[BankNode] =>
      val edgeNewSeq = edge.nodeSeq
      val edgeNewP1 = edgeNewSeq.head
      val edgeNewP2 = edgeNewSeq.tail.head
      val route = getRouteByNodes(edgeNewP1, edgeNewP2, routes: Routes)

      (edgeNewP1 ~% edgeNewP2)(route.time + getJam(route, traffic))
    }
  }

  def getRouteByNodes(node1: BankNode, node2: BankNode, routes: Routes): EdgeRoute = {
    routes.routes.find(route => route.a == node1.id & route.b == node2.id).get
  }

  def updateNodeEdges(nodes: Seq[BankNode],
                      edges: Seq[WUnDiEdge[BankNode]],
                      car: Car): Seq[WUnDiEdge[BankNode]] = {
    car.currentNodeId match {
      case 0 =>
        val start = findNodeById(nodes, 0).get.asInstanceOf[Start]
        val startNew = addStartNodeById(nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        edges.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == start) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == start) :+ startNew
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == node) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == node) :+ nodeNew
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }

      case 1 =>
        val vault = findNodeById(nodes, 1).get.asInstanceOf[Vault]
        val vaultNewSum = addVaultNodeById(nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        edges.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == vault) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == vault) :+ vaultNewSum
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == node) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == node) :+ nodeNew
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }

      case i =>
        val point = findNodeById(nodes, i).get.asInstanceOf[Point]
        val pointNewCash = addPointNodeById(i, nodes, car)
        val node = findNodeByCar(nodes, car).get
        val nodeNew = delCarFromNode(node, car)

        edges.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == point) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == point) :+ pointNewCash
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }.map { edge: WUnDiEdge[BankNode] =>
          edge.find(_ == node) match {
            case Some(_) =>
              val edgeNewSeq = edge.nodeSeq.filterNot(_ == node) :+ nodeNew
              val edgeNewP1 = edgeNewSeq.head
              val edgeNewP2 = edgeNewSeq.tail.head
              (edgeNewP1 ~% edgeNewP2)(edge.weight)
            case _ => edge
          }
        }
    }
  }

  def findNodeById(nodes: Seq[BankNode], id: Int): Option[BankNode] =
    nodes.find(node => node.id == id)

  def findNodeByCar(nodes: Seq[BankNode], car: Car): Option[BankNode] =
    nodes.find(node => node.cars.contains(car))

  def addCarToNode(node: BankNode, car: Car): BankNode = {
    node.addCar(car)
    node
  }

  def delCarFromNode(node: BankNode, car: Car): BankNode = {
    node.delCar(car)
    node
  }

  def updateVaultNode(node: Vault, car: Car): Vault = Vault(node.getData.copy(
    node.getData.id, node.getData.money + car.cash, node.getData.cars :+ car
  ))

  def addCarToPointNode(node: Point, car: Car): Point = Point(node.getData.copy(
    node.getData.id, node.getData.money, node.getData.carCashIn, node.getData.cars :+ car, node.getData.timeRemain
  ))

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
