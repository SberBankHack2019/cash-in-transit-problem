package com.sberhack.fun.struct

package object requests {

  case class Init(team: String = "SamurAI")

  case class GoTo(goto: Int, // Point
                  car: String,
                  nomoney: Boolean)

  case class Reconnect(reconnect: String) // Token

}
