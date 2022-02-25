package com.skidis.wordle
package simulation

import BlockColor.{BlockColor, Green}
import nerdle.NerdleGuessableGenerator
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

  val results: List[List[(String, List[BlockColor])]] = Await.result(
    Future.sequence(answers.map(answer => runWordle(answer.phrase))), 1.hour
  ).toList
  printResults(results)

  val endTimestamp = System.currentTimeMillis()
  println(s"Time Elapsed: ${(endTimestamp - startTimestamp) / 1000}")

  def runWordle(answer: String): Future[List[(String, List[BlockColor])]] = Future {
    val processor = new SimulationNerdleProcessor(answer) with ClusterStrategy
    val result = processor.process(equations, startEquation)
    result
  }

  abstract class SimulationNerdleProcessor(answer: String) extends SimulationProcessor(answer) {
    override def winningColorPattern: ColorPattern = List.fill(8)(Green)
  }
}
