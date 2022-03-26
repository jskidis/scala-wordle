package com.skidis.wordle
package wordle

import TestFixtures.TWord

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class WordleRunnerIntegrationTests extends AnyFunSuite with Matchers {
  val slimAnswerSet: Set[TWord] = Set("SLATE", "TRACE", "JAZZY").map(TWord)

  def didAnySimulationsReturnErrors(linesWritten: ListBuffer[String]): Boolean = {
    linesWritten.exists(l => l.contains("-1 Guesses"))
  }

  test("Run Wordle Simulation Apps", IntegrationTest) {
    val standardFixture = new WordleSimulationStandardRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    standardFixture.main(Array())
    didAnySimulationsReturnErrors(standardFixture.linesWritten) must not be true

    val answerOnlyFixture = new WordleSimulationAnswerOnlyRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    answerOnlyFixture.main(Array())
    didAnySimulationsReturnErrors(answerOnlyFixture.linesWritten) must not be true

    val charFreqFixture = new WordleSimulationCharFreqRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    charFreqFixture.main(Array())
    didAnySimulationsReturnErrors(charFreqFixture.linesWritten) must not be true

    val wordFreqFixture = new WordleSimulationWordFreqRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    wordFreqFixture.main(Array())
    didAnySimulationsReturnErrors(wordFreqFixture.linesWritten) must not be true

    val reverseFixture = new WordleSimulationReverseRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = slimAnswerSet
    }
    reverseFixture.main(Array())
    didAnySimulationsReturnErrors(reverseFixture.linesWritten) must not be true
  }
}
