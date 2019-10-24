package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class PointData (
                       id: Long,
                       money: Double,
                       carCashIn: Option[Car],
                       cars: Seq[Car],
                       timeRemain: Double
                     )
