package com.sberhack.fun.graph.vertex

class Vault(player: String) extends BankBuilding{
  override def vertexText: String = s"$player Vault"
}

object Vault {
  def apply(player: String): Vault = new Vault(player)
}
