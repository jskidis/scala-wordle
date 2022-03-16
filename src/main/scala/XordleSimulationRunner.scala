package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

trait XordleSimulationRunner extends XordleRunner with SimulationProcessFactory {
  def runSimulation(): Unit = {
    val startTimestamp = System.currentTimeMillis()

    val answers = answerSet.toSeq.map(_.phrase)
    val processor = createSimulationProcessor()

    val results = for {
      result <- Await.result(Future.sequence(
        answers.map{ answer => Future(runWordle(processor, answer)) }
      ), 1.hour)
    } yield result
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(processor: SimulationProcessor, answer: String): Int = {
    processor.process(guessSet, answer) match {
      case Left(_) => -1
      case Right(result) => result.size
      //    println(s"Processes: $answer - ${result.size} Guesses")
    }
  }

  def printResults(results: Seq[Int]): Unit = {
    val grouped = results.groupBy(i=> i).map {
      case (i: Int, seq: Seq[Int]) => (i, seq.size)
    }.toVector.sortBy(_._1)

    grouped.foreach {
      case (numGuesses, count) =>
        val percent = 100.0 * count / results.size
        println(f"$numGuesses Guesses: $count%5d ($percent%5.2f%%)")
    }

    val successful = grouped.filter(t => t._1 > 0 && t._1 <= 6)
    val avgGuesses = successful.map {
      case (numGuesses, count) => numGuesses * count
    }.sum * 1.0 / results.count(i => i > 0 && i <= 6)

    println(f"Avg Guesses: $avgGuesses%1.3f")
  }
}
