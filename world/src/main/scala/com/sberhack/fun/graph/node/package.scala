package com.sberhack.fun.graph

import com.sberhack.fun.graph.configuration.config

package object node {

  private val cashPrecision = config.vertex.vsp.cashPrecision
  private val timePrecision = config.vertex.vsp.cashPrecision

  def getPrecision: (Int, Int) = {(cashPrecision, timePrecision)}
//  private val vspMinCash = roundDouble(config.vertex.vsp.minCash, cashPrecision)
//  private val vspMaxCash = roundDouble(config.vertex.vsp.maxCash, cashPrecision)
//  private val vspMinTime = roundDouble(config.vertex.vsp.minPickupTime, timePrecision)
//  private val vspMaxTime = roundDouble(config.vertex.vsp.maxPickupTime, timePrecision)
//
//  def createVSP: Point = createVSPnoID(
//    (vspMinCash, vspMaxCash),
//    (vspMinTime, vspMaxTime)
//  )
//
//  def createVSP(id: Int): Point = createVSP(
//    id,
//    (vspMinCash, vspMaxCash),
//    (vspMinTime, vspMaxTime)
//  )
//
//  private def createVSP(
//                         id: Int,
//                         cashBounds: (Double, Double),
//                         timeBounds: (Double, Double)
//                       ): Point =
//    Point(
//      Some(id),
//      roundDouble(rnd.nextDouble(cashBounds._1, cashBounds._2), cashPrecision),
//      roundDouble(rnd.nextDouble(timeBounds._1, timeBounds._2), timePrecision)
//    )
//
//  private def createVSPnoID(
//                             cashBounds: (Double, Double),
//                             timeBounds: (Double, Double)
//                           ): Point =
//    Point(
//      None,
//      roundDouble(rnd.nextDouble(cashBounds._1, cashBounds._2), cashPrecision),
//      roundDouble(rnd.nextDouble(timeBounds._1, timeBounds._2), timePrecision)
//    )


}
