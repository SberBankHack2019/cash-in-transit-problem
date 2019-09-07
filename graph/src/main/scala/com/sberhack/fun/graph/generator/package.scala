package com.sberhack.fun.graph

import com.sberhack.fun.graph.struct.VSP
import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.graph.configuration.config
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge


package object generator {

  private[generator] def distance = roundDouble(
    rnd.nextDouble(config.distance.minDistance, config.distance.maxDistance),
    config.distance.precision
  )

  /* Сгенерировать граф, где вершины будут идти одна за другой вот так: н-н-н-н-н */
  def genLinkedSberGraph(size: Int): Graph[VSP, WUnDiEdge] = SimpleLinkedGraph.genGraph(size)

}