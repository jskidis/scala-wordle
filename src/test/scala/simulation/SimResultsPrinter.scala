package com.skidis.wordle
package simulation

import BlockColor.BlockColor

trait SimResultsPrinter {
  def printResults(results: List[List[(String, List[BlockColor])]]): Unit = {
    val groupedByGuesses = results.groupBy {
      case Nil => -1
      case result => result.size
    }.toList.sortWith(_._1 < _._1)

    val grouped = groupedByGuesses
    grouped.foreach {
      case (numGuesses, resultSet) =>
        val numResults = resultSet.size
        val percent = 100.0 * resultSet.size / results.size
        println(f"$numGuesses Guesses: $numResults%5d ($percent%5.2f%%)")
    }
    val avgGuesses = grouped.map { case (nG, rs) => nG * rs.size }.sum * 1.0 / results.size
    println(f"Avg Guesses: $avgGuesses%1.3f")
  }
}
