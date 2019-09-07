package com.sberhack.fun.graph.generator
import com.sberhack.fun.graph.struct.{VSP, createVSP}
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._

private[generator] object SimpleLinkedGraph extends GraphGenerator{
  override def genGraph(size: Int): Graph[VSP, WUnDiEdge] = {

    var k: VSP = createVSP
    var l: VSP = createVSP

    Graph.fill[VSP, WUnDiEdge](size)( { k = l; l = createVSP; (k ~% l)(distance) })

  }
}
