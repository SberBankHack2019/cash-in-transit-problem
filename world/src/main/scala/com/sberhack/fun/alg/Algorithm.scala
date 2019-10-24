package com.sberhack.fun.alg

import com.sberhack.fun.car.Car
import com.sberhack.fun.world.structure.World
import scalax.collection.Graph

abstract class Algorithm {

  def calculateNextActions(world: World): Seq[Car]

  val maxDepth: Int

}


object Algorithm extends Algorithm {

  override def calculateNextActions(world: World): Seq[Car] = {

    world.carsWithoutTask match {
      case Seq() => Seq() // Нет машин которым нечего делать
      case cars => ???
    }

  }

  override val maxDepth: Int = 3

}