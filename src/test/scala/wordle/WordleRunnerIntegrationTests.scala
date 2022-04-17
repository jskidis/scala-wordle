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

  val answers: WordSet = WordReader.readWords(Source.fromResource("answers.txt"))
  val simulationAnswers: WordSet = Random.shuffle(answers.toSeq).take(3).toSet
  //  val simulationAnswers = WordReader.readWords(Source.fromResource("recent-answers.txt"))
  val answerOfTheDay = "CHEEK" //Random.shuffle(answers).headOption.map(_.text).getOrElse("")


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
    val answerOnlyFixture = new XrdleSimulationRunner with WordleAnswerOnlyRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(answerOnlyFixture, "Answer-Only")

    val standardFixture = new XrdleSimulationRunner with WordleStandardRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(standardFixture, "All three (Standard)")

    val charAndWordFreqFixture = new XrdleSimulationRunner with WordleCharFreqRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(charAndWordFreqFixture, "Char And Word Freq")

    val wordFreqFixture = new XrdleSimulationRunner with WordleWordFreqRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(wordFreqFixture, "Word Freq Only")

    val clusterAndBothFreqFixture = new XrdleSimulationRunner with WordleClusterAndBothFreqRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(clusterAndBothFreqFixture, "Cluster And Both Freq")

    val clusterOnlyFixture = new XrdleSimulationRunner with WordleClusterOnlyRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(clusterOnlyFixture, "Cluster Only")

    val clusterCharFixture = new XrdleSimulationRunner with WordleClusterAndCharFreqRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(clusterCharFixture, "Cluster And Char Freq")

    val charOnlyFreqFixture = new XrdleSimulationRunner with WordleCharFreqOnlyRunner {
      override lazy val answerSet: WordSet = simulationAnswers
    }
    validateAndPrintSimulations(charOnlyFreqFixture, "Char Freq Only")
  }

  // I use the test to "interactively" run all of the different runner with known word of the day
  // just to see how they all do on a given day
  test("Run Wordle Interactive Runners", IntegrationTest) {

    trait InteractiveProcessorFixture extends InteractiveProcessor {
      override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
      override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
        generateWordHints(answerOfTheDay, guess)
    }

    val answerOnlyFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleAnswerOnlyRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleAnswerOnlyInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(answerOnlyFixture, "Answer-Only")

    val standardFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleStandardRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleStandardInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(standardFixture, "Cluster And Word Freq (Standard)")

    val charAndWordFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleCharFreqRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleCharFreqInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(charAndWordFreqFixture, "Char And Word Freq")

    val wordFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleWordFreqRunner  {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleWordFreqInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(wordFreqFixture, "Word Freq Only")

    val clusterAndBothFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleClusterAndBothFreqRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleClusterAndBothFreqProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(clusterAndBothFreqFixture, "Cluster And Both Freq")

    val clusterAndCharFreqFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleClusterAndCharFreqRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleClusterAndCharFreqProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(clusterAndCharFreqFixture, "Cluster and Char Freq")

    val charFreqOnlyFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleCharFreqOnlyRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleCharFreqOnlyProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(charFreqOnlyFixture, "Char Freq Only")

    val clusterOnlyFixture: XrdleInteractiveRunner = new XrdleInteractiveRunner with WordleClusterOnlyRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new WordleClusterOnlyProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(clusterOnlyFixture, "Cluster Only")

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
