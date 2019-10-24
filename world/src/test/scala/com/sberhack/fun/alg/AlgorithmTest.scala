package com.sberhack.fun.alg

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import com.sberhack.fun.alg.Algorithm._
import com.sberhack.fun.car.{Car, CarNextMove}
import com.sberhack.fun.world.structure.World
import scalax.collection.Graph

class AlgorithmTest extends AnyFreeSpec with Matchers {

 "Algorithm" - {

   val car1 = Car("car1", 10, 10, 10, Some(CarNextMove(1, true)))
   val car2 = Car("car2", 10, 10, 10, Some(CarNextMove(2, false)))
   val car3 = Car("car3", 10, 10, 10, Some(CarNextMove(2, true)))

   val noNextActionsWorld = World(
     "Every car busy world",
     "TOKEN",
     1,
     Graph.empty,
     Seq(car1, car2, car3)
   )

   "Should return empty Seq when all cars are assigned " - {
      calculateNextActions(noNextActionsWorld) shouldBe Seq()
   }

 }

}
