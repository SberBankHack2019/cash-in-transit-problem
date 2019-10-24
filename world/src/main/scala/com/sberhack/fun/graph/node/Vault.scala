package com.sberhack.fun.graph.node

class Vault(data: VaultData) extends BankNode{
  override def vertexText: String = s"< VAULT: $data >"

  def getData: VaultData = data
}

object Vault {
  def apply(data: VaultData): Vault = new Vault(data)
}
