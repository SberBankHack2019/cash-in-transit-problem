package com.sberhack.fun.graph.generator


private[generator] final case class MaxRetriesReachedException(private val message: String = "Max retries in graph generation reached, probably generators end with endless recursion",
                                            private val cause: Throwable = None.orNull) extends Exception(message, cause)