package com.skidis.wordle.nerdle

import com.skidis.wordle.{IntegrationTest, WordSet, WriterToBuffer}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class NerdleRunnerIntegrationTests extends AnyFunSuite with Matchers {
  // This will generate error if text is not a valid equation
  def generateEquation(text: String): NerdleEquation = {
    EquationParser.parseEquation(text) match { case Right(eq) => eq }
  }

  def didAnySimulationsReturnErrors(linesWritten: ListBuffer[String]): Boolean = {
    linesWritten.exists(l => l.contains("-1 Guesses"))
  }

  test("Run Wordle Simulation Apps", IntegrationTest) {
    val standardFixture = new NerdleSimulationStandardRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = Set(
        generateEquation("54-38=16"),
        generateEquation("47+21=68"),
        generateEquation("6*8/12=4")
      )
    }
    standardFixture.main(Array())
    didAnySimulationsReturnErrors(standardFixture.linesWritten) must not be true

    val miniFixture = new NerdleSimulationMiniRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = Set(
        generateEquation("14-8=6"),
        generateEquation("7+8=15"),
        generateEquation("15/3=5")
      )
    }
    miniFixture.main(Array())
    didAnySimulationsReturnErrors(miniFixture.linesWritten) must not be true
  }
}