package com.sberhack.fun.graph

import pureconfig._
import pureconfig.generic.auto._

package object configuration {

  private[graph] val config = loadConfigOrThrow[GraphConfig]

}
