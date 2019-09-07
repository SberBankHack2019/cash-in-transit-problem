package com.sberhack.fun.graph

import java.util.concurrent.ThreadLocalRandom

package object utils {

  def rnd: ThreadLocalRandom = ThreadLocalRandom.current()

  def roundDouble(d: Double, scale: Int = 3): Double = {
    BigDecimal(d).setScale(scale, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

}
