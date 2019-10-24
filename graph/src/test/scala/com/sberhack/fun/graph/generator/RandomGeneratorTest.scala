package com.sberhack.fun.graph.generator

//import com.sberhack.fun.graph.node.{BankNode, Point, createVSP}
import org.scalatest.{FreeSpec, Matchers}
//import scalax.collection.constrained
//import scalax.collection.constrained.Config
//import scalax.collection.constrained.constraints.Connected
//import scalax.collection.edge.Implicits._
//import scalax.collection.edge.WUnDiEdge
//
//import scala.collection.immutable

class RandomGeneratorTest extends FreeSpec with Matchers {

  private val generator = SimpleRandomGraph
//
//  "Take N Vsps" - {
//
//    val vsps: immutable.Seq[Point] = (1 to 10).map(createVSP)
//    val takenVsps = generator.takeNRandomVSPs(vsps, 4)
//
//    "Should have correct size" in { takenVsps.size shouldBe 4 }
//
//    "Should not contain duplicates" in { takenVsps.toSet.size shouldBe 4 }
//
//    "Should be taken from vsps correctly" in { (vsps diff takenVsps).size shouldBe 6 }
//
//  }
//
//  "Gen Graph" - {
//
//   "should throw exception if generator function cant produce connected graph" in {
//     def notConnectedGraph(size: Int): constrained.Graph[BankNode, WUnDiEdge] = {
//
//       val edges = Seq(
//         (createVSP(1) ~% createVSP(2))(distance),
//         (createVSP(5) ~% createVSP(6))(distance)
//       )
//       implicit val conf: Config = Connected
//
//       constrained.Graph.from(Seq(), edges)
//     }
//
//     an [MaxRetriesReachedException] must be thrownBy generator.generateUntilConnected(2, notConnectedGraph, 10)
//
//   }
//
//  }

}
