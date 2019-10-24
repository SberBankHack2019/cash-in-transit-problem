package com.sberhack.fun.alg

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import Algorithm._
import com.sberhack.fun.car.Car
import com.sberhack.fun.world.structure.World
import scalax.collection.Graph

class AlgorithmTest extends AnyFreeSpec with Matchers {

 "Algorithm" - {

   val car1 = Car("car1", 10, 10, 10, None)
   val car2 = Car("car1", 10, 10, 10, None)
   val car3 = Car("car1", 10, 10, 10, None)

   val noNextActionsWorld = World(
     "Every car busy world",
     1,
     Graph.empty,
     Seq(car1, car2, car3)
   )

   "Should return empty Seq when all cars are assigned " - {
      calculateNextActions(noNextActionsWorld) shouldBe Seq()
   }

 }

}
