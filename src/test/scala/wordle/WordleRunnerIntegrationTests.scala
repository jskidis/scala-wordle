package com.skidis.wordle
package wordle

import TestFixtures.TWord

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

class WordleRunnerIntegrationTests extends AnyFunSuite with Matchers {
  val slimAnswerSet: Set[TWord] = Set("SLATE", "TRACE", "JAZZY").map(TWord)

  def didAnySimulationsReturnErrors(results: (Seq[Int], Long)): Boolean = {
    val guesses = results._1
    guesses.contains(-1)
  }

  test("Run Wordle Simulation Apps", IntegrationTest) {
    val standardFixture = new WordleSimulationStandardRunner {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    didAnySimulationsReturnErrors(standardFixture.runSimulation()) must not be true

    val answerOnlyFixture = new WordleSimulationAnswerOnlyRunner {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    didAnySimulationsReturnErrors(answerOnlyFixture.runSimulation()) must not be true

    val charFreqFixture = new WordleSimulationCharFreqRunner {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    didAnySimulationsReturnErrors(charFreqFixture.runSimulation()) must not be true

    val wordFreqFixture = new WordleSimulationWordFreqRunner {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    didAnySimulationsReturnErrors(wordFreqFixture.runSimulation()) must not be true

    val reverseFixture = new WordleSimulationReverseRunner {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    didAnySimulationsReturnErrors(reverseFixture.runSimulation()) must not be true
  }

/*
  test("Run Wordle Interactive Runners", IntegrationTest) {
    val standardFixture = new WordleInteractiveStandardRunner {
    }
    standardFixture.run()

  }
*/
}
