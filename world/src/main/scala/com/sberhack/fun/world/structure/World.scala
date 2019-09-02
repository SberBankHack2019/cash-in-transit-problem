package com.sberhack.fun.world.structure

import com.sberhack.fun.alg.structure.Wagon
import com.sberhack.fun.graph.Graph

case class World (
                   name: String,
                   version: Long,
                   world: Graph,
                   wagons: (Wagon, Wagon)
                 )
