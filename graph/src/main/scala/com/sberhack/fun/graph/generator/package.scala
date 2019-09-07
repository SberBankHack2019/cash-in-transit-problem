package com.sberhack.fun.graph

import com.sberhack.fun.graph.struct.{VSP, createVSP}
import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.graph.configuration.config
import scalax.collection.Graph
import scalax.collection.GraphPredef.{EdgeLikeIn, Param}
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._

import scala.reflect.ClassTag

package object generator {

  private def distance = roundDouble(rnd.nextDouble(config.distance.minDistance, config.distance.maxDistance), config.distance.precision)

  /* Сгенерировать граф, где вершины будут идти одна за другой вот так: н-н-н-н-н */
  def genLinkedSberGraph(size: Int): Graph[VSP, WUnDiEdge] = {

    var k: VSP = createVSP
    var l: VSP = createVSP

    Graph.fill[VSP, WUnDiEdge](size)( { k = l; l = createVSP; (k ~% l)(distance) })

  }


}