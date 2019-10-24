package com.sberhack.fun.alg

import com.sberhack.fun.alg.Algorithm.Path
import com.sberhack.fun.graph.node.Point

package object functions {

  def SergeyFunction(path: Path): Double = {
    path.collect{ case p: Point => p.getData.money }.sum
  }

}
