package com.sberhack.fun.graph.generator

import com.sberhack.fun.car.{Car, CarConfig}
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
  graph.nodes.foreach(node => println(s"node: $node"))
  graph.edges.foreach(edge => println(s"edge: $edge"))

  saveDotFile("graph", graph.toDotConfigured)
}
