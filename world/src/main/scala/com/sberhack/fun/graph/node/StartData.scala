package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

case class StartData(
                      id: Long,
                      cars: Seq[Car]
                    )