package com.sberhack.fun.graph.node

class Vault(data: VaultData) extends BankNode{
  override def vertexText: String = s"Vault data: $data"
  override val id: Int = data.id
}

object Vault {
  def apply(data: VaultData): Vault = new Vault(data)
}
