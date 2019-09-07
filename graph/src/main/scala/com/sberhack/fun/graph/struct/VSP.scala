package com.sberhack.fun.graph.struct

case class VSP(
                cash: Long, // Количество денег, которое машина сможет забрать из отделения в рублях
                pickupTime: Long // Время, которое потребуется машине для забора денег в секундах
              ){

  override def toString: String =
    s"""
       | Количество денег: $cash руб
       | Время на сбор: ${pickupTime / 60} м ${pickupTime % 60} c
     """.stripMargin

}
