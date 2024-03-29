package com.sberhack.fun.graph.generator

import com.sberhack.fun.car.{Car, CarConfig, CarNextMove}
import com.sberhack.fun.graph.dot._
import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.struct.responses.{EdgeRoute, EdgeTraffic, GameConfig, Points, Routes, Traffic, VertexPoint}
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

import scala.collection.mutable

object Test extends App {
  val config: GameConfig = GameConfig("12345", List("sb1", "sb2", "sb3", "sb4"), 1)
  val carConfig = CarConfig()
  val cars: mutable.Seq[Car] = mutable.Seq(config.cars.map(Car(_, 0, carConfig.cashLimitDefault, 0, None)): _*)
  val points: Points = Points(List(
    VertexPoint(0, 10000),
    VertexPoint(1, 10000),
    VertexPoint(2, 10000)
    ))
  val routes: Routes = Routes(List(
    EdgeRoute(0, 1, 12),
    EdgeRoute(1, 2, 3),
    EdgeRoute(2, 0, 9)
  ))
  val traffic: Traffic = Traffic(List(
    EdgeTraffic(0, 1, "1.0"),
    EdgeTraffic(1, 2, "1.1"),
    EdgeTraffic(2, 0, "1.3")
  ))

  val graph: Graph[BankNode, WUnDiEdge] = createGraph(
    config, cars, points, routes, traffic
  )

  println(s"Graph size: ${graph.graphSize}")
  graph.nodes.foreach(node => println(s"CREATE node: $node"))
  graph.edges.foreach(edge => println(s"CREATE edge: ${edge.weight.toLong}, $edge"))

  saveDotFile("graph", graph.toDotConfigured)

  val trafficNew: Traffic = Traffic(List(
    EdgeTraffic(0, 1, "2.0"),
    EdgeTraffic(1, 2, "1.7"),
    EdgeTraffic(2, 0, "1.7")
  ))

  val carNew: Car = Car("sb1", 0.0, carConfig.cashLimitDefault, 2, Some(CarNextMove(2, true)))

  //update trafficNew
  val graphNew1: Graph[BankNode, WUnDiEdge] = updateGraph(
    graph, routes, trafficNew
  )

  //update car
  val graphNew2: Graph[BankNode, WUnDiEdge] = updateGraph(
    graphNew1, carNew
  )

  println(s"Graph size: ${graphNew2.graphSize}")
  graphNew2.nodes.foreach(node => println(s"UPDATE node: $node"))
  graphNew2.edges.foreach(edge => println(s"UPDATE edge: ${edge.weight.toLong}, $edge"))

  saveDotFile("graphNew", graphNew2.toDotConfigured)
}
