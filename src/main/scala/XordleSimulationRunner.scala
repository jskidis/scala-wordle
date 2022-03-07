package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

trait XordleSimulationRunner extends XordleRunner {
  def runSimulation(): Unit = runSimulation(None)

  def runSimulation(suppliedStartGuess: Option[String]): Unit = {
    val startTimestamp = System.currentTimeMillis()

    val answers = answerSet.toSeq.map(_.phrase)
    val theStartingGuess = suppliedStartGuess.getOrElse(startGuess)
    val processor = createSimulationProcessor()

    println(s"Starting Word: $theStartingGuess")
    val results = for {
      result <- Await.result(Future.sequence(
        answers.map{ answer => Future(runWordle(processor, answer, theStartingGuess)) }
      ), 1.hour)
    } yield result
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(processor: SimulationProcessor, answer: String, theStartingGuess: String): Int = {
    processor.process(guessSet, theStartingGuess, answer) match {
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
    val avgGuesses = grouped.filter(_._1 > 0).map { case (numGuesses, count) => numGuesses * count }.sum * 1.0 / results.size
    println(f"Avg Guesses: $avgGuesses%1.3f")
  }
}
