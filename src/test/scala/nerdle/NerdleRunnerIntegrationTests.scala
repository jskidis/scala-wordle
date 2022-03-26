package com.skidis.wordle.nerdle

import com.skidis.wordle.{IntegrationTest, WordSet, WriterToBuffer}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

class NerdleRunnerIntegrationTests extends AnyFunSuite with Matchers {
  // This will generate error if text is not a valid equation
  def generateEquation(text: String): NerdleEquation = {
    EquationParser.parseEquation(text) match { case Right(eq) => eq }
  }

  def didAnySimulationsReturnErrors(results: (Seq[Int], Long)): Boolean = {
    val guesses = results._1
    guesses.contains(-1)
  }

  test("Run Wordle Simulation Apps", IntegrationTest) {
    val standardFixture = new NerdleSimulationStandardRunner {
      override lazy val answerSet: WordSet = Set(
        generateEquation("54-38=16"),
        generateEquation("47+21=68"),
        generateEquation("6*8/12=4")
      )
    }
    didAnySimulationsReturnErrors(standardFixture.runSimulation()) must not be true

    val miniFixture = new NerdleSimulationMiniRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = Set(
        generateEquation("14-8=6"),
        generateEquation("7+8=15"),
        generateEquation("15/3=5")
      )
    }
    didAnySimulationsReturnErrors(miniFixture.runSimulation()) must not be true
  }
}