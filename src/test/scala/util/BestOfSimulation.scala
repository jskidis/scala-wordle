package com.skidis.wordle
package util

import wordle.{WordleAnswerOnlyRunner, WordleCharFreqRunner, WordleStandardRunner}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object BestOfSimulation extends App {
  runSimulation()

  def runSimulation(): Unit = {
    val startTimestamp = System.currentTimeMillis()

    val runners = Seq(
      new XrdleSimulationRunner with WordleStandardRunner,
      new XrdleSimulationRunner with WordleAnswerOnlyRunner,
      new XrdleSimulationRunner with WordleCharFreqRunner
    )

    val answers = runners.head.answerSet.toSeq.map {a: XrdleWord => a.text }

    val results = for {
      result <- Await.result(Future.sequence(
        answers.map{ answer =>
          Future {
            val runResults = runners.map{ runner =>
              runWordle(runner.createSimulationProcessor(), answer, runner.guessSet)
            }
            if (runResults.forall(_ > 6)) println(answer)
            runResults.min
          }
        }
      ), 1.hour)
    } yield result
    printResults(results)

    val endTimestamp = System.currentTimeMillis()
    println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")
  }

  def runWordle(processor: SimulationProcessor, answer: String, guessSet: WordSet): Int = {
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
    val avgGuesses = grouped.filter(t => t._1 > 0 && t._1 <=6).map { case (numGuesses, count) => numGuesses * count }.sum * 1.0 / results.size
    println(f"Avg Guesses: $avgGuesses%1.3f")
  }
}
