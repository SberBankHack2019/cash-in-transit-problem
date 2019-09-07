package com.sberhack.fun.graph

import com.sberhack.fun.graph.configuration.config
import com.sberhack.fun.graph.vertex.BankBuilding
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.io.dot.{DotAttr, DotAttrStmt, DotEdgeStmt, DotGraph, DotRootGraph, Elem, implicits}
import scalax.collection.io.dot._
import implicits._

package object dot {

  private val graphName = config.dot.graphName

  private val nodeShapeDotConf = DotAttrStmt(Elem.node, List(DotAttr("shape",  config.dot.nodeShape)))

  implicit private[graph] class GraphToDot(graph: Graph[BankBuilding, WUnDiEdge]) {

    private val dotRoot = DotRootGraph(directed = false,
      id = Some(graphName),
      attrStmts = List(nodeShapeDotConf),
    )

    private def edgeTransformer(innerEdge: Graph[BankBuilding, WUnDiEdge]#EdgeT):
      Option[(DotGraph, DotEdgeStmt)] = innerEdge.edge match {
        case WUnDiEdge(source, target, weight) => Some(
          (dotRoot, DotEdgeStmt(source.toString, target.toString, Seq(DotAttr("label", weight.toString))))
      )
    }

    def toDotConfigured: String = graph.toDot(dotRoot, edgeTransformer)

  }

  private[graph] def saveDotFile(fileName: String, dot: String): Unit = {
    import java.io.PrintWriter
    new PrintWriter(s"graph/src/main/generated/dot/$fileName.dot") { write(dot); close() }
  }


}
