package com.sberhack.fun.graph

import com.sberhack.fun.graph.vertex.BankBuilding
import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.graph.configuration.config
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge


package object generator {

  private[generator] def distance = roundDouble(
    rnd.nextDouble(config.vertex.vsp.distance.minDistance, config.vertex.vsp.distance.maxDistance),
    config.vertex.vsp.distance.precision
  )

  /* Сгенерировать граф, где вершины будут идти одна за другой вот так: н-н-н-н-н */
  def genLinkedSberGraph(size: Int): Graph[BankBuilding, WUnDiEdge] = SimpleLinkedGraph.genGraph(size)

  /* Сгененировать случайный граф
   * где условие на минимальное максимальное количество нод может не выполняться
   * */
  def genSimpleRandomGraph(size: Int): Graph[BankBuilding, WUnDiEdge] = SimpleRandomGraph.genGraph(size)

}