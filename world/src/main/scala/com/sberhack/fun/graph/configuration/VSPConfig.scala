package com.sberhack.fun.graph.configuration

private[configuration] case class VSPConfig(
                                             minCash: Double,
                                             maxCash: Double,
                                             minPickupTime: Double,
                                             maxPickupTime: Double,
                                             cashPrecision: Int,
                                             timePrecision: Int,
                                             distance: DistanceConfig
                                           )