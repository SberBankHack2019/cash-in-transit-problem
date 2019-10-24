package com.sberhack.fun.alg

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import com.sberhack.fun.alg.Algorithm._
import com.sberhack.fun.car.{Car, CarNextMove}
import com.sberhack.fun.graph.node.{BankNode, Point, PointData, Start, StartData, Vault, VaultData}
import com.sberhack.fun.world.structure.World
import scalax.collection.Graph
import TestGraph._
import scalax.collection.edge.WUnDiEdge
import scalax.collection.edge.Implicits._
import com.sberhack.fun.graph.dot._
import scalax.collection.GraphPredef.OuterElem

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

  /*
        def genPossibleRoutes(graph: Graph[BankNode, WUnDiEdge], currentNode: BankNode, depth: Int): Unit/*: Seq[(BankNode, (Seq[BankNode], Double))]*/ = {

  val visitedNodes = scala.collection.mutable.HashMap[BankNode, Boolean]()

        val graphNodes = graph.nodes.map(_.toOuter)

        val graphSize = graphNodes.size

        graphNodes.foreach{ node =>
          visitedNodes.put(node, false)
        }

        val queue = scala.collection.mutable.Queue[BankNode]()

        visitedNodes(currentNode) = true

        queue.enqueue(currentNode)



      //  visitedNodes.foreach{ case (node, visited) => println(s"$node is $visited")}

        while (queue.nonEmpty) {
          scala.util.control.Breaks.breakable{
            val node = queue.dequeue()
            //println(s"Start processing $node")
            //println()
            for (cur <- graphNodes.filterNot(_ == node)){
              //println(cur)
              if (!visitedNodes(cur)){
                visitedNodes(cur) = true
                println(visitedNodes(cur))
                queue.enqueue(cur)
                if(queue.size == (graphSize - 1) * depth){
                  scala.util.control.Breaks.break()
                }
              }
            }
          }
        }

        queue.foreach(println)
      }

   */


}
