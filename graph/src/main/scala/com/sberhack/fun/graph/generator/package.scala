package com.sberhack.fun.graph

import com.sberhack.fun.graph.configuration.config
import com.sberhack.fun.graph.node.BankNode
import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.struct.responses.{Points, Routes, Traffic}
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge


package object generator {


  def createWorldGraph(points: Points, routes: Routes, traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    ???
  }

  def updateWorldGraph(points: Points, traffic: Traffic): Graph[BankNode, WUnDiEdge] = {
    ???
  }

  private[generator] def distance = roundDouble(
    rnd.nextDouble(config.vertex.vsp.distance.minDistance, config.vertex.vsp.distance.maxDistance),
    config.vertex.vsp.distance.precision
  )

  /* Сгенерировать граф, где вершины будут идти одна за другой вот так: н-н-н-н-н */
  def genLinkedSberGraph(size: Int): Graph[BankNode, WUnDiEdge] = SimpleLinkedGraph.genGraph(size)

  /* Сгененировать случайный граф
   * где условие на минимальное максимальное количество нод может не выполняться
   * */
  def genSimpleRandomGraph(size: Int): Graph[BankNode, WUnDiEdge] = SimpleRandomGraph.genGraph(size)

}