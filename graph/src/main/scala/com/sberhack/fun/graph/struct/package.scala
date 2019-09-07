package com.sberhack.fun.graph

import com.sberhack.fun.graph.utils.rnd
import com.sberhack.fun.graph.configuration.config

package object struct {


  def createVSP: VSP = createVSP(
    (config.vsp.minValue, config.vsp.maxValue),
    (config.vsp.minPickupTime, config.vsp.maxPickupTime)
  )

  private def createVSP(
                 cashBounds: (Long, Long),
                 timeBounds: (Long, Long)
               ): VSP =
    VSP(
      rnd.nextLong(cashBounds._1, cashBounds._2),
      rnd.nextLong(timeBounds._1, timeBounds._2)
    )


}
