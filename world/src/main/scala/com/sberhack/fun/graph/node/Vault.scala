package com.sberhack.fun.graph.node

import com.sberhack.fun.car.Car

import scala.collection.mutable

class Vault(data: VaultData) extends BankNode{
  override def vertexText: String = s"Vault data: $data"
  override val id: Int = data.id
  override var cars: mutable.Seq[Car] = data.cars

  def getData: VaultData = data
}

object Vault {
  def apply(data: VaultData): Vault = new Vault(data)
}
