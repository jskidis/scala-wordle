package com.skidis.wordle.nerdle

import com.skidis.wordle.hintgen.WordHintsGenerator
import com.skidis.wordle.nerdle.runner._
import com.skidis.wordle.runners.{InteractiveProcessor, XrdleInteractiveRunner, XrdleSimulationRunner}
import com.skidis.wordle.{IntegrationTest, WordHints, WordSet, WriterToBuffer}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class NerdleRunnerIntegrationTests extends AnyFunSuite with Matchers
  with WordHintsGenerator with NerdleHintProps {

  val equations8Char: WordSet = NerdleGuessableGenerator.generate8CharEquations()
  val equations6Char: WordSet = NerdleGuessableGenerator.generate6CharEquations()

  def didAnySimulationsReturnErrors(results: (Seq[Int], Long)): Boolean = {
    val guesses = results._1
    guesses.contains(-1)
  }

  def validateAndPrintSimulations(fixture: XrdleSimulationRunner, name: String): Unit = {
    println(s"Nerdle $name Simulation")
    println(Seq.fill(40)('*').mkString)
    val results = fixture.runSimulation()
    didAnySimulationsReturnErrors(results) must not be true
    fixture.printResults(results)
    println("")
  }

  def validateAndPrintInteractive(fixture: XrdleInteractiveRunner, name: String): Unit = {
    println(s"Nerdle $name Runner")
    val results = fixture.runInteractive()
    results.isRight mustBe true
    fixture.printWordleBlock(results, 6)
    println(Seq.fill(40)('*').mkString)
    println()
  }

  test("Run Nerdle Simulation Apps", IntegrationTest) {
    val standardFixture = new NerdleSimulationStandardRunner {
      override lazy val answerSet: WordSet = Random.shuffle(equations8Char.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(standardFixture, "Standard")

    val miniFixture = new NerdleSimulationMiniRunner with WriterToBuffer {
      override lazy val answerSet: WordSet = Random.shuffle(equations6Char.toSeq).take(3).toSet
    }
    validateAndPrintSimulations(miniFixture, "Mini")
  }

  test("Run Nerdle Interactive Runner, automating the interaction", IntegrationTest) {
    val answerOfTheDay = Random.shuffle(equations8Char.toSeq).headOption.map(_.text)

    trait InteractiveProcessorFixture extends InteractiveProcessor {
      override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
      override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
        generateWordHints(answerOfTheDay.getOrElse(""), guess)
    }

    val miniAnswerOfTheDay = Random.shuffle(equations6Char.toSeq).headOption.map(_.text)
    trait MiniInteractiveProcessorFixture extends InteractiveProcessor {
      override def retrieveGuess(suggestions: Seq[String]): String = suggestions.headOption.getOrElse("")
      override def retrieveWordHints(guess: String, answer: Option[String]): WordHints =
        generateWordHints(miniAnswerOfTheDay.getOrElse(""), guess)
    }

    val standardFixture: XrdleInteractiveRunner = new NerdleInteractiveStandardRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new NerdleStandardInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(standardFixture, "Standard")

    val miniFixture: XrdleInteractiveRunner = new NerdleInteractiveMiniRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new NerdleMiniInteractiveProcessor with MiniInteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(miniFixture, "Mini")

    val randomFixture: XrdleInteractiveRunner = new NerdleInteractiveRandomRunner {
      override def createInteractiveProcessor(): InteractiveProcessor = {
        new NerdleRandomInteractiveProcessor with InteractiveProcessorFixture {}
      }
      override def inputPuzzleNumber(): String = ""
    }
    validateAndPrintInteractive(randomFixture, "Random")
  }
}