package com.sberhack.fun.graph

import com.sberhack.fun.graph.utils.{rnd, roundDouble}
import com.sberhack.fun.graph.configuration.config

package object vertex {

  private val cashPrecision = config.vertex.vsp.cashPrecision
  private val timePrecision = config.vertex.vsp.cashPrecision
  private val vspMinCash = roundDouble(config.vertex.vsp.minCash, cashPrecision)
  private val vspMaxCash = roundDouble(config.vertex.vsp.maxCash, cashPrecision)
  private val vspMinTime = roundDouble(config.vertex.vsp.minPickupTime, timePrecision)
  private val vspMaxTime = roundDouble(config.vertex.vsp.maxPickupTime, timePrecision)

  def createVSP: VSP = createVSPnoID(
    (vspMinCash, vspMaxCash),
    (vspMinTime, vspMaxTime)
  )

  def createVSP(id: Int): VSP = createVSP(
    id,
    (vspMinCash, vspMaxCash),
    (vspMinTime, vspMaxTime)
  )

  private def createVSP(
                         id: Int,
                         cashBounds: (Double, Double),
                         timeBounds: (Double, Double)
                       ): VSP =
    VSP(
      Some(id),
      roundDouble(rnd.nextDouble(cashBounds._1, cashBounds._2), cashPrecision),
      roundDouble(rnd.nextDouble(timeBounds._1, timeBounds._2), timePrecision)
    )

  private def createVSPnoID(
                             cashBounds: (Double, Double),
                             timeBounds: (Double, Double)
                           ): VSP =
    VSP(
      None,
      roundDouble(rnd.nextDouble(cashBounds._1, cashBounds._2), cashPrecision),
      roundDouble(rnd.nextDouble(timeBounds._1, timeBounds._2), timePrecision)
    )


}
