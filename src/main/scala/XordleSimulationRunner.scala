package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

trait XordleSimulationRunner extends XordleRunner {
  def runSimulation(): Unit = {
    val startTimestamp = System.currentTimeMillis()

    val answers = answerSet.toSeq.map(_.phrase)
    val processor = createSimulationProcessor()

    val results = for {
      result <- Await.result(Future.sequence(
        answers.map{ answer => runWordle(processor, answer) }
      ), 1.hour)
    } yield result
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(processor: SimulationProcessor, answer: String): Future[Seq[(String, WordHints)]] = Future {
    val result = processor.process(guessSet, startGuess, answer)
//    println(s"Processes: $answer - ${result.size} Guesses")
    result
  }

  def printResults(results: Seq[Seq[(String, WordHints)]]): Unit = {
    val groupedByGuesses = results.groupBy {
      case Nil => 0
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
