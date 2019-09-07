package com.sberhack.fun.world.structure

import com.sberhack.fun.alg.structure.Wagon
import com.sberhack.fun.graph.vertex.VSP
import scalax.collection.Graph
import scalax.collection.edge.WUnDiEdge

case class World (
                   name: String,
                   version: Long,
                   world: Graph[VSP, WUnDiEdge],
                   wagons: (Wagon, Wagon)
                 )
