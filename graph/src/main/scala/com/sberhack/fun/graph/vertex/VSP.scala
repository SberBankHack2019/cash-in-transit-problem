package com.sberhack.fun.graph.vertex

class VSP(
           id: Option[Int] = None,
           cash: Double,
           pickupTime: Double
         ) extends BankBuilding {

  override def vertexText: String = id match {
    case Some(_) => textWithId
    case None    => textWithoutId
  }

  private def pickupTimeToDuration: String = {
    s"${(pickupTime / 60).toLong} м ${(pickupTime % 60).toLong} c"
  }

  private def textWithoutId: String =
    s"""
       | Количество денег: ${cash.toLong} руб
       | Время на сбор: $pickupTimeToDuration
     """.stripMargin

  private def textWithId: String =
    s"""
       | Номер ВСП: ${id.getOrElse("None")}
       | Количество денег: ${cash.toLong} руб
       | Время на сбор: $pickupTimeToDuration
     """.stripMargin

}

object VSP {
  def apply(id: Option[Int], cash: Double, pickupTime: Double): VSP = new VSP(id, cash, pickupTime)
}
