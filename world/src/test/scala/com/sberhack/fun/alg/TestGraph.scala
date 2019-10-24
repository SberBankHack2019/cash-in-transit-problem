package com.sberhack.fun.alg

import com.sberhack.fun.alg.Algorithm.Path
import com.sberhack.fun.car.Car
import com.sberhack.fun.graph.node.{BankNode, Point, PointData, Start, StartData, Vault, VaultData}
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._

object TestGraph {

  val carAtStart = Car("car1", 0, 10000, 0, None)

  val start: Start = Start(StartData(0, Seq(carAtStart)))
  val node1: Point = Point(PointData(2, 20, None, Seq(), 100))
  val node2: Point = Point(PointData(3, 50, None, Seq(), 100))
  val node3: Point = Point(PointData(4, 40, None, Seq(), 100))
  val vault: Vault = Vault(VaultData(1, 0, Seq()))

  val nodes: Seq[BankNode] = Seq(start, node1, node2, node3, vault)

  val edges: Seq[WUnDiEdge[BankNode]] = Seq(
    (start ~% node1)(5),
    (start ~% node2)(10),
    (start ~% node3)(40),
    (node1 ~% vault)(30),
    (node2 ~% vault)(70),
    (node3 ~% vault)(10),
    (node1 ~% node2)(15),
    (node2 ~% node3)(20),
    (node1 ~% node3)(10),
    (start ~% vault)(60)
  )


  val graph = Graph.from(nodes, edges)

  val allTwoDepthPaths: Seq[(BankNode, Path)] = Seq(
    (node1, Seq(node1, node2)), // 70
    (node1, Seq(node1, node3)), // 60
    (node1, Seq(node1, vault)), // 20
    (node2, Seq(node2, node1)), // 70
    (node2, Seq(node2, node3)), // 90
    (node2, Seq(node2, vault)), // 50
    (node3, Seq(node3, node1)), // 60
    (node3, Seq(node3, node2)), // 90
    (node3, Seq(node3, vault)), // 40
    (vault, Seq(vault, node1)), // 20
    (vault, Seq(vault, node2)), // 50
    (vault, Seq(vault, node3)) // 40
  )

  def easyTestFunction(path: Path): Double = {
    path.collect{ case p: Point => p.getData.money }.sum
  }

}
