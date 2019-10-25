package com.sberhack.fun.world

import com.sberhack.fun.car.{Car, CarConfig, CarNextMove}
import com.sberhack.fun.graph.generator._
import com.sberhack.fun.struct.responses.{DestinationPoint, GameConfig, Points, Routes, Traffic}
import com.sberhack.fun.alg.Algorithm.calculateNextActions

import scala.collection.mutable

object World {
  val defaultCapacity = 50
  val worldName = "My Shiny World"

  var worldStructure: structure.World = _
  var worldRoutes: Routes = _
  var nextActions: Seq[Car] = _

  def create(config: GameConfig,
             points: Points,
             routes: Routes,
             traffic: Traffic
             ): Unit = {
    worldRoutes = routes
    val carConfig = CarConfig()
    nextActions = Seq(Car("sb0", 0, carConfig.cashLimitDefault, 0, Option(CarNextMove(2, true))))
    val cars: Seq[Car] = config.cars.map(Car(_, 0, carConfig.cashLimitDefault, 0, None))
    val worldGraph = createGraph(config, mutable.Seq[Car](cars: _*), points, routes, traffic)
    worldStructure = structure.World(worldName, config.token, config.level, worldGraph, cars)
  }

  def update(traffic: Traffic) = {
    val worldGraph = updateGraph(worldStructure.world, worldRoutes, traffic)
    worldStructure = worldStructure.copy(world=worldGraph)
  }

  def update(dp: DestinationPoint) = {
    val updatedCar = Car(dp.car, if (dp.point == 1) 0 else dp.carsum, CarConfig().cashLimitDefault, dp.point, None)
    val cars = worldStructure.cars.filter(_.name != dp.car) :+ updatedCar

    nextActions = calculateNextActions(worldStructure)

    val worldGraph = updateGraph(worldStructure.world, updatedCar)
    worldStructure = worldStructure.copy(world=worldGraph, cars=cars)
  }

}
