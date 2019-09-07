package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.struct.VSP
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

trait GraphGenerator {

  def genGraph(size: Int): Graph[VSP, WUnDiEdge]

}
