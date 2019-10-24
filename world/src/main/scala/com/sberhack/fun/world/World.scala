package com.sberhack.fun.world

import com.sberhack.fun.car.Car
import com.sberhack.fun.graph.generator._
import com.sberhack.fun.struct.responses.{GameConfig, Points, Routes, Traffic}
import com.sberhack.fun.world.structure.World

abstract class World {
  val defaultCapacity = 50
  val worldName = "My Shiny World"

  var worldStructure: structure.World = _

  def create(config: GameConfig,
             points: Points,
             routes: Routes,
             traffic: Traffic
             ): Unit = {
    val cars: Seq[Car] = config.cars.map(Car(_, 0, defaultCapacity, 0, None))
    val worldGraph = createWorldGraph(points, routes, traffic)
    worldStructure = World(worldName, config.token, config.level, worldGraph, cars)
  }


  def update(config: GameConfig,
             points: Points,
             routes: Routes,
             traffic: Traffic
            ): Unit = {
    val cars: Seq[Car] = config.cars.map(Car(_, 0, defaultCapacity, 0, None))
    val worldGraph = updateWorldGraph(points, traffic)
    worldStructure = World(worldName, config.token, config.level, worldGraph, cars)
  }

}
