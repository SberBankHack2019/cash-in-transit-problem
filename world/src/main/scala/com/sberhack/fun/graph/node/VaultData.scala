package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class VaultData (
                       id: Int,
                       money: Double, // Количество денег которые уже отвезены в хранилище
                       cars: Seq[Car]
                     )
