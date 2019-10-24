package com.sberhack.fun.world.structure

import com.sberhack.fun.alg.structure.Car
import com.sberhack.fun.graph.node.BankNode
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

case class World (
                   name: String,
                   token: String,
                   level: Long,
                   world: Graph[BankNode, WUnDiEdge],
                   cars: Seq[Car]
                 )
