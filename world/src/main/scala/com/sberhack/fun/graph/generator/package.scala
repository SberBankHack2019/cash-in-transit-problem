package com.sberhack.fun.graph

import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.struct.responses.{Points, Routes, Traffic}
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

package object generator {

  def createGraph(points: Points, routes: Routes, traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    ???
  }

  def updateGraph(points: Points, traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    ???
  }

}