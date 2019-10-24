package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.vertex.{BankBuilding, Vault, createVSP}
import scalax.collection.Graph
import scalax.collection.edge.Implicits._
import scalax.collection.edge.WUnDiEdge

private[generator] object SimpleLinkedGraph extends GraphGenerator{
  override def genGraph(size: Int): Graph[BankBuilding, WUnDiEdge] = {

    var k: BankBuilding = Vault("Player1")
    var l: BankBuilding = Vault("Player1")

    // Маловероятно, но может содержать дубли
    Graph.fill[BankBuilding, WUnDiEdge](size)( { k = l; l = createVSP; (k ~% l)(distance) })

  }
}
