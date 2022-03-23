package com.skidis.wordle

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

trait XrdleSimulationRunner extends XrdleRunner with SimulationProcessFactory {

  def runMultipleSimulations(startingGuessesSets: Seq[Seq[String]]): Seq[(Seq[String], Double)] = {
    startingGuessesSets.map { startingGuesses: Seq[String] =>
      val (guessCounts, _) = runSimulation(startingGuesses)
      (startingGuesses, determineAvgGuesses(guessCounts, groupGuessCounts(guessCounts)))
    }
  }

  def runSimulation(startingGuesses: Seq[String] = Nil): (Seq[Int], Long) = {
    def runWordle(processor: SimulationProcessor, answer: String): Int = {
      processor.process(guessSet, answer) match {
        case Left(_) => -1
        case Right(result) => result.size
      }
    }

    val startTimestamp = System.currentTimeMillis()

    val answers = answerSet.toSeq.map(_.text)
    val processor = createSimulationProcessor(startingGuesses)

    val guessCounts = for {
      result <- Await.result(Future.sequence(
        answers.map{ answer => Future(runWordle(processor, answer)) }
      ), 1.hour)
    } yield result

    val timeElapsed = System.currentTimeMillis() - startTimestamp
    (guessCounts, timeElapsed)
  }

  def printResults(results: (Seq[Int], Long)): Unit = results match {
    case(gc: Seq[Int], te: Long) => printResults(gc, te)
  }
  def printResults(guessCounts: Seq[Int], timeElapsed: Long): Unit = {
    val grouped = groupGuessCounts(guessCounts)
    val avgGuesses = determineAvgGuesses(guessCounts, grouped)

    grouped.foreach {
      case (numGuesses, count) =>
        val percent = 100.0 * count / guessCounts.size
        println(f"$numGuesses Guesses: $count%5d ($percent%5.2f%%)")
    }

    println(f"Avg Guesses: $avgGuesses%1.3f")
    println(s"Time Elapsed: ${timeElapsed / 1000.0}")
  }

  def groupGuessCounts(results: Seq[Int]): Vector[(Int, Int)] = {
    results.groupBy(i => i).map {
      case (i: Int, seq: Seq[Int]) => (i, seq.size)
    }.toVector.sortBy(_._1)
  }

  def determineAvgGuesses(results: Seq[Int], grouped: Vector[(Int, Int)]): Double = {
    val successful = grouped.filter(t => t._1 > 0 && t._1 <= 6)
    successful.map {
      case (numGuesses, count) => numGuesses * count
    }.sum * 1.0 / results.count(i => i > 0 && i <= 6)
  }
}
