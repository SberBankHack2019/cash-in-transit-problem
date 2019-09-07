package com.sberhack.fun.graph

import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.io.dot.{DotAttr, DotAttrStmt, DotEdgeStmt, DotGraph, DotRootGraph, Elem, implicits}
import scalax.collection.io.dot._
import implicits._

package object dot {

  implicit private[graph] class GraphToDot[T](graph: Graph[T, WUnDiEdge]) {

    private val graphName = "SberbankVSPmap"

    private val dotRoot = DotRootGraph(directed = false,
      id = Some(graphName),
      attrStmts = List(DotAttrStmt(Elem.node, List(DotAttr("shape", "circle")))),
    )

    private def edgeTransformer(innerEdge: Graph[T, WUnDiEdge]#EdgeT):
      Option[(DotGraph, DotEdgeStmt)] = innerEdge.edge match {
        case WUnDiEdge(source, target, weight) => Some(
          (dotRoot, DotEdgeStmt(source.toString, target.toString, Seq(DotAttr("label", weight.toString))))
      )
    }

    def toDotPredef: String = graph.toDot(dotRoot, edgeTransformer)

  }

  private[graph] def saveDotFile(fileName: String, dot: String): Unit = {
    import java.io.PrintWriter
    new PrintWriter(s"graph/src/main/generated/dot/$fileName.dot") { write(dot); close() }
  }


}
