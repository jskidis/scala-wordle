package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

trait XordleSimulationRunner extends XordleRunner {
  def runSimulation(): Unit = {
    val startTimestamp = System.currentTimeMillis()

    val results: Vector[Seq[(String, WordHints)]] = Await.result(
      Future.sequence(
        answerSet.map {answer:XordlePhrase => runWordle(answer.phrase)}), 1.hour
    ).toVector
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(answer: String): Future[Seq[(String, WordHints)]] = Future {
    val processor = createSimulationProcessor(answer)
    val result = processor.process(guessSet, startGuess)
    //    println("Processed")
    result
  }

  def printResults(results: Vector[Seq[(String, WordHints)]]): Unit = {
    val groupedByGuesses = results.groupBy {
      case Nil => -1
      case result => result.size
    }.toVector.sortWith(_._1 < _._1)

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
