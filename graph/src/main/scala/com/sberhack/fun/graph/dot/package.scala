package com.sberhack.fun.graph

import java.io.{File, PrintWriter}

import com.sberhack.fun.graph.configuration.config
import com.sberhack.fun.graph.vertex.BankBuilding
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.io.dot._
import scalax.collection.io.dot.implicits._

import scala.sys.process._

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
    val dotFileName = s"$fileName.dot"
    val dotFilePath = "graph/src/main/generated/dot"
    val dotFilePathF = new File(dotFilePath)

    if(!dotFilePathF.exists()){
      dotFilePathF.mkdir()
    }

    new PrintWriter(s"$dotFilePath/$dotFileName") {
      write(dot)
      close()
    }
  }

  private[graph] def echo(message: String): Unit = {
    val currentTime: String = "date \"+%Y-%m-%d %H:%M:%S.%N\""

    s"echo [TIME] $$( $currentTime ) [INFO] $message".!
  }

  private[graph] def prettyEcho(message: String): Unit = {
    Process("echo ------------------------------------").run()
    echo(message)
    Process("echo ------------------------------------").run()
  }

  private[graph] def exec(shFullPathName: String, shArgs: List[String]): Unit = {
    echo(s"exec $shFullPathName with Args: ${shArgs.mkString(",")}")
    Process(s"$shFullPathName ${shArgs.mkString(" ")}").run()
  }

  private[graph] def createPngFromDotFile(fileName: String,
                                          shFileName: String,
                                          shFileArgs: List[String]): Unit = {
    val dotFullFileName = s"$fileName.dot"
    val dotFilePath = "graph/src/main/generated/dot"

    val pngFullFileName = s"$fileName.png"
    val pngFilePath = "graph/src/main/generated/png"
    val dotFilePathF = new File(pngFilePath)

    val shFullFileName = s"$shFileName.sh"
    val shFilePath = "graph/src/main/generated"

    if(!dotFilePathF.exists()){
      dotFilePathF.mkdir()
    }

    prettyEcho("***Creating .PNG image from .DOT file***")
    echo(s"Full .DOT file PathName: $dotFilePath/$dotFullFileName")
    echo(s"Full .PNG file PathName: $pngFilePath/$pngFullFileName")
    exec(s"$shFilePath/$shFullFileName", shFileArgs)

  }

}
