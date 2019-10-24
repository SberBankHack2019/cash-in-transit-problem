package com.sberhack.fun.world

import com.sberhack.fun.car.{Car, CarConfig, CarNextMove}
import com.sberhack.fun.graph.generator._
import com.sberhack.fun.struct.responses.{DestinationPoint, GameConfig, Points, Routes, Traffic}
import com.sberhack.fun.alg.Algorithm.calculateNextActions

object World {
  val defaultCapacity = 50
  val worldName = "My Shiny World"

  var worldStructure: structure.World = _
  var nextActions: Seq[Car] = _

  def create(config: GameConfig,
             points: Points,
             routes: Routes,
             traffic: Traffic
             ): Unit = {
    val carConfig = CarConfig()
    val cars: Seq[Car] = config.cars.map(Car(_, 0, carConfig.cashLimitDefault, 0, None))
    val worldGraph = createGraph(config, cars, points, routes, traffic)
    worldStructure = structure.World(worldName, config.token, config.level, worldGraph, cars)
  }

  def update(traffic: Traffic) = {
    ???
    //val worldGraph = updateGraph(traffic)
    //worldStructure = worldStructure.copy(traffic=traffic)
  }

  def update(dp: DestinationPoint) = {
    val updatedCar = Car(dp.car, dp.carsum, CarConfig().cashLimitDefault, dp.point, None)
    val cars = worldStructure.cars.filter(_.name != dp.car) :+ updatedCar

    nextActions = calculateNextActions(worldStructure)

    val worldGraph = updateGraph(cars)
    worldStructure = worldStructure.copy(world=worldGraph)

  }

}
