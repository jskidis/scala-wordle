package com.skidis.wordle
package simulation

import nerdle.{NerdleGuessableGenerator, NerdleHintProps}
import strategy.ClusterStrategy

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.Random

object NerdleSimulation extends App with SimResultsPrinter {
  val startEquation = "58-42=16"
  val equations = NerdleGuessableGenerator.generatateEquations()
  val answers = Random.shuffle(equations)

  val startTimestamp = System.currentTimeMillis()

  val results: Vector[Seq[(String, WordHints)]] = Await.result(
    Future.sequence(answers.map(answer => runWordle(answer.phrase))), 1.hour
  ).toVector
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

  def runWordle(answer: String): Future[Seq[(String, WordHints)]] = Future {
    val processor = new SimulationProcessor(answer, NerdleHintProps) with ClusterStrategy
    val result = processor.process(equations, startEquation)
    result
  }
}
