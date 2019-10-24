package com.sberhack.fun.graph

import com.sberhack.fun.graph.dot._
import com.sberhack.fun.graph.generator._
import com.sberhack.fun.graph.vertex.BankBuilding
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

object Main extends App{

  val simpleGraph: Graph[BankBuilding, WUnDiEdge] = genSimpleRandomGraph(5)
  val graphFileName = "attempt1"

  println(simpleGraph.toDotConfigured)
  saveDotFile(graphFileName, simpleGraph.toDotConfigured)
//  createPngFromDotFile(graphFileName, "dotToFile", List(graphFileName, "png"))

}
