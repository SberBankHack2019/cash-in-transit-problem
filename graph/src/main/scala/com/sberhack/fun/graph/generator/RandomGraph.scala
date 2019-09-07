package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.configuration.config
import com.sberhack.fun.graph.vertex.{BankBuilding, VSP, Vault, createVSP}
import com.sberhack.fun.graph.utils.rnd
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._

import scala.collection.immutable

object RandomGraph extends GraphGenerator {

  private val minConnections = config.generator.random.minConnections
  private val maxConnections = config.generator.random.maxConnections

  private val minBaseConnections = config.generator.random.minBaseConnections
  private val maxBaseConnections = config.generator.random.maxBaseConnections

  private def nConnections = rnd.nextInt(minConnections, maxConnections + 1)

  private val nBaseConnections = rnd.nextInt(minBaseConnections, maxBaseConnections + 1)

  private def takeRandomVSP(vsp: VSP, vsps: Seq[VSP]) = {
    val n = rnd.nextInt(0, vsps.size - 1)
    vsps.filterNot(_.equals(vsp))(n)
  }

  private def takeRandomVSP(vsps: Seq[VSP]) = {
    val n = rnd.nextInt(0, vsps.size)
    vsps(n)
  }

  override def genGraph(size: Int): Graph[BankBuilding, WUnDiEdge] = {

    val player1Vault = Vault("Player1")
    val player2Vault = Vault("Player2")

    val vsps: Seq[VSP] = (0 to size).map(createVSP)

    val vspConnections: Seq[WUnDiEdge[VSP]] = vsps.flatMap { vsp =>
      (1 to nConnections).map(_ => (vsp ~% takeRandomVSP(vsp, vsps))(distance))
    }

    val player1Connections: immutable.Seq[WUnDiEdge[BankBuilding]] = {
      (1 to nBaseConnections).map(_ => (player1Vault ~% takeRandomVSP(vsps))(distance))
    }

    val player2Connections: immutable.Seq[WUnDiEdge[BankBuilding]] = {
      (1 to nBaseConnections).map(_ => (player2Vault ~% takeRandomVSP(vsps))(distance))
    }

    val nodes: Seq[BankBuilding] = vsps :+ player1Vault :+ player2Vault

    val edges: Seq[WUnDiEdge[BankBuilding]] = vspConnections ++ player1Connections ++ player2Connections

    Graph.from(nodes, edges)

  }

}
