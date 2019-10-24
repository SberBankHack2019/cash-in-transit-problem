package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class VaultData (
                       id: Long,
                       money: Double,
                       cars: Seq[Car]
                     )
