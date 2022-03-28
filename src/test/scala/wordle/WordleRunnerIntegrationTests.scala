package com.skidis.wordle
package wordle

import hintgen.WordHintsGenerator
import runners.{InteractiveProcessor, XrdleInteractiveRunner, XrdleSimulationRunner}
import wordle.runner._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import scala.io.Source
import scala.util.Random

class WordleRunnerIntegrationTests extends AnyFunSuite with Matchers
  with WordHintsGenerator with WordleHintProps {

  val answers: Set[SimpleWordleWord] = WordReader.readWords(Source.fromResource("answers.txt"))

  def didAnySimulationsReturnErrors(results: (Seq[Int], Long)): Boolean = {
    val guesses = results._1
    guesses.contains(-1)
  }

  def validateAndPrintSimulations(fixture: XrdleSimulationRunner, name: String): Unit = {
    println(s"Wordle $name Simulation")
    println(Seq.fill(40)('*').mkString)
    val results = fixture.runSimulation()
    didAnySimulationsReturnErrors(results) must not be true
    fixture.printResults(results)
    println("")
  }

  def validateAndPrintInteractive(fixture: XrdleInteractiveRunner, name: String): Unit = {
    println(s"Wordle $name Runner")
    val results = fixture.runInteractive()
    results.isRight mustBe true
    fixture.printWordleBlock(results, 6)
    println(Seq.fill(40)('*').mkString)
    println()
  }

  test("Run Wordle Simulation Apps", IntegrationTest) {
    val standardFixture = new XrdleSimulationRunner with WordleStandardRunner {
      override lazy val answerSet: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(standardFixture, "Standard")

    val answerOnlyFixture = new XrdleSimulationRunner with WordleAnswerOnlyRunner {
      override lazy val answerSet: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(answerOnlyFixture, "Answer-Only")

    val charFreqFixture = new XrdleSimulationRunner with WordleCharFreqRunner {
      override lazy val answerSet: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(charFreqFixture, "Char Frequency")

    val wordFreqFixture = new XrdleSimulationRunner with WordleWordFreqRunner {
      override lazy val answerSet: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(wordFreqFixture, "Word Frequency")

    val reverseFixture = new XrdleSimulationRunner with WordleReverseRunner {
      override lazy val answerSet: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(reverseFixture, "Reverse")
  }

  test("Run Wordle Interactive Runners", IntegrationTest) {
    val answerOfTheDay = Random.shuffle(answers).headOption.map(_.text).getOrElse("")

    trait InteractiveProcessorFixture extends InteractiveProcessor {
      override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
      override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
        generateWordHints(answerOfTheDay, guess)
    }

    val standardFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleStandardRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleStandardInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(standardFixture, "Standard")

    val answerOnlyFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleAnswerOnlyRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleAnswerOnlyInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(answerOnlyFixture, "Answer-Only")

    val charFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleCharFreqRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleCharFreqInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(charFreqFixture, "Char Frequency")

    val wordFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleWordFreqRunner  {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleWordFreqInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(wordFreqFixture, "Word Frequency")

    val reverseFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleReverseRunner  {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleReverseInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(reverseFixture, "Reverse")

    val randomFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleRandomRunner  {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleRandomInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(randomFixture, "Random")
  }
}
