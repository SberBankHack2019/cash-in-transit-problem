package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.node.BankNode
import scalax.collection.Graph
//import scalax.collection.edge.Implicits._
import scalax.collection.edge.WUnDiEdge

private[generator] object SimpleLinkedGraph extends GraphGenerator{
  override def genGraph(size: Int): Graph[BankNode, WUnDiEdge] = {
    Graph.fill[BankNode, WUnDiEdge](size)(???)
  }
//  override def genGraph(size: Int): Graph[BankNode, WUnDiEdge] = {
//
//    val kData = VaultData()
//    val lData = VaultData()
//
//    var k: BankNode = Vault(kData)
//    var l: BankNode = Vault(lData)
//
//    // Маловероятно, но может содержать дубли
//    //Graph.fill[BankNode, WUnDiEdge](size)( { k = l; l = createVSP; (k ~% l)(distance) })
//
//  }
}
