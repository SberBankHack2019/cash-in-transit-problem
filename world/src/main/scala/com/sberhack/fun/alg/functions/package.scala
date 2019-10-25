package com.sberhack.fun.alg

import com.sberhack.fun.alg.Algorithm.{Path, WeightedPath}
import com.sberhack.fun.graph.node.Point

package object functions {

  def SergeyFunction(path: Path): Double = {
    path.collect{ case p: Point => p.getData.money }.sum
  }

  def SergeyWeightedFunction(path: WeightedPath): Double = {
    path.collect{ case (p: Point, weight) => p.getData.money / weight}.sum
  }

}
