package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.node.BankNode
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

trait GraphGenerator {

  def genGraph(size: Int): Graph[BankNode, WUnDiEdge]

}
