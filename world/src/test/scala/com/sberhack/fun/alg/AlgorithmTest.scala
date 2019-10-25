package com.sberhack.fun.alg

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import com.sberhack.fun.alg.Algorithm._
import com.sberhack.fun.car.{Car, CarNextMove}
import com.sberhack.fun.world.structure.World
import scalax.collection.Graph
import TestGraph._

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


    "Find all pathes should work correctly" - {
      genPossibleRoutes(graph, start).toSet shouldBe allTwoDepthPaths.toSet
    }

    "Best Path should Start with" - {
      findBestPath(genPossibleRoutes(graph, start).map(_._2), easyTestFunction) shouldBe node1
    }
  }

  "Filter should work correctly" - {
    filterNotNode(graph, start) shouldBe graphWithoutStart
  }

  "AA" - {
    genWeightedRoutes(graph, start).foreach(println)
  }

  "naiveAlgo" - {
    "Should return best Node if everything is ok" - {
      naiveAlgorithm(graph, start, 1000) shouldBe Some((node2.getData.id, node2.getData.timeRemain))
    }

    "Return None if there is no space for money" - {
      naiveAlgorithm(graph, start, 0) shouldBe None
    }

  }

}
