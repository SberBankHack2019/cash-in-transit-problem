package com.sberhack.fun.graph

import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.graph.configuration.config

package object struct {

  private def cashPrecision = config.vsp.cashPrecision
  private def timePrecision = config.vsp.cashPrecision
  private def vspMinCash = roundDouble(config.vsp.minCash, cashPrecision)
  private def vspMaxCash = roundDouble(config.vsp.maxCash, cashPrecision)
  private def vspMinTime = roundDouble(config.vsp.minPickupTime, timePrecision)
  private def vspMaxTime = roundDouble(config.vsp.maxPickupTime, timePrecision)

  def createVSP: VSP = createVSP(
    (vspMinCash, vspMaxCash),
    (vspMinTime, vspMaxTime)
  )

  private def createVSP(
                 cashBounds: (Double, Double),
                 timeBounds: (Double, Double)
               ): VSP =
    VSP(
      roundDouble(rnd.nextDouble(cashBounds._1, cashBounds._2), cashPrecision),
      roundDouble(rnd.nextDouble(timeBounds._1, timeBounds._2), timePrecision)
    )


}
