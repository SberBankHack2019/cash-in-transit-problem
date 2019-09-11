package com.sberhack.fun.graph.generator

import com.sberhack.fun.graph.configuration.config
import com.sberhack.fun.graph.vertex.{BankBuilding, VSP, Vault, createVSP}
import com.sberhack.fun.graph.utils.rnd
import scalax.collection.constrained.{Config, Graph}
import scalax.collection.constrained
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._
import scalax.collection.constrained.constraints.Connected

import scala.collection.immutable

private[generator] object SimpleRandomGraph extends GraphGenerator {

  private val minConnections = config.generator.random.minConnections
  private val maxConnections = config.generator.random.maxConnections

  private val minBaseConnections = config.generator.random.minBaseConnections
  private val maxBaseConnections = config.generator.random.maxBaseConnections

  private[generator] def nConnections = rnd.nextInt(minConnections, maxConnections + 1)

  // val чтобы у обоих игроков было одинаковое количество соединений с общим графом
  private val nBaseConnections = rnd.nextInt(minBaseConnections, maxBaseConnections + 1)

  private def takeRandomVSP(vsp: VSP, vsps: Seq[VSP]) = {
    val n = rnd.nextInt(0, vsps.size - 1)
    vsps.filterNot(_.equals(vsp))(n)
  }

  private def takeRandomVSP(vsps: Seq[VSP]) = {
    val n = rnd.nextInt(0, vsps.size)
    vsps(n)
  }

  // Пока нигде не используется
  private[generator] def takeNRandomVSPs(vsps: Seq[VSP], n: Int): Seq[VSP] = {

    @scala.annotation.tailrec
    def go(vsps: Seq[VSP], vspsTaken: Seq[VSP], nLeft: Int): Seq[VSP] = {
      if (nLeft == 0) vspsTaken
      else {
        val newVspsTaken = vspsTaken :+ takeRandomVSP(vsps)
        go(vsps diff newVspsTaken, newVspsTaken, nLeft - 1)
      }
    }

    go(vsps, Seq(), n)
  }

  override def genGraph(size: Int): Graph[BankBuilding, WUnDiEdge] = {
    generateUntilConnected(size, genUnsafeGraph, 10)
  }

  private[generator] def generateUntilConnected(size: Int, graphGen: Int => constrained.Graph[BankBuilding, WUnDiEdge], maxRetries: Int): constrained.Graph[BankBuilding, WUnDiEdge] ={
    /* Перегенерировать граф, пока он не будет цельным */
    val graph = graphGen(size)

    if (maxRetries == 0){
      throw new MaxRetriesReachedException
    } else {
      if (graph.isEmpty) {
        println("Got graph that contains not connected parts, regenerating")
        generateUntilConnected(size, graphGen, maxRetries - 1)
      }
      else graph
    }

  }


  private def genUnsafeGraph(size: Int): constrained.Graph[BankBuilding, WUnDiEdge] = {

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

    implicit val conf: Config = Connected

    // Граф с constraints connected возвращает пустой граф, если не все части графа соединены
    constrained.Graph.from(nodes, edges)

  }

}
