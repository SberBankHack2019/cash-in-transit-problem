package com.sberhack.fun.struct

import io.circe.generic.AutoDerivation

package object responses extends AutoDerivation {

  case class GameConfig(token: String,
                        cars: List[String],
                        level: Int)

  case class EdgeRoute(a: Int,
                       b: Int,
                       time: Int)

  case class Routes(routes: List[EdgeRoute])

  case class EdgeTraffic(a: Int,
                         b: Int,
                         jam: String)

  case class Traffic(traffic: List[EdgeTraffic])

  case class VertexPoint(p: Int,
                         money: Int)

  case class Points(points: List[VertexPoint])

  case class DestinationPoint(point: Int,
                              car: String,
                              carsum: Long)

  case class VaultPoint(teamsum: Long)
}
