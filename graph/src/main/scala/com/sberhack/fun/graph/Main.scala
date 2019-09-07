package com.sberhack.fun.graph

import com.sberhack.fun.graph.dot._
import com.sberhack.fun.graph.generator._

object Main extends App{

  saveDotFile("attempt2", genLinkedSberGraph(15).toDotConfigured)

}


