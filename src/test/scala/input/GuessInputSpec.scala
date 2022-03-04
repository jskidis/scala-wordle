package com.skidis.wordle
package input

import TestFixtures.{TestGuessProps, TestHintProps}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class GuessInputSpec extends AnyFunSpec with Matchers {
  val suggestions: Vector[String] = Vector("guess", "xylyl")
  val validInput:String = "abcde"

  class GuessInputFixture(inputs: Vector[String]) extends GuessInput with TestGuessProps with TestHintProps {
    var linesRead = 0
    var linesWritten = new ListBuffer[String]()

    override def readLine(): String = {
      linesRead = linesRead +1
      inputs(linesRead -1)
    }

    override def writeLine(s: String): Unit = linesWritten.addOne(s)
    override def writeString(s: String): Unit = linesWritten.addOne(s)

    val validationMsg = "Some validation failed"
    override def validateGuess(s: String): Option[String] = {
      if (s == validInput.toUpperCase()) None
      else Some(validationMsg)
    }
  }


  describe("Gather Guess") {
    it("returns the suggested word if the input is empty") {
      val guessInput = new GuessInputFixture(Vector(""))
      val result = guessInput.getGuessFromInput(suggestions)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestions.head.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestions)
    }

    it("returns the suggested word if the trim of input is empty") {
      val guessInput = new GuessInputFixture(Vector("  "))
      val result = guessInput.getGuessFromInput(suggestions)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestions.head.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestions)
    }

    it("returns results from reader when valid result is entered on first try") {
      val guessInput = new GuessInputFixture(Vector(validInput))
      val result = guessInput.getGuessFromInput(suggestions)

      // It should return a result and that result should be equal to "validResult" value
      result mustBe validInput.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestions)
    }

    it("re-asks for results if not valid") {
      val guessInput = new GuessInputFixture(Vector("12345", validInput))
      val result = guessInput.getGuessFromInput(suggestions)

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result mustBe validInput.toUpperCase

      // It should have called the line reader 2 times
      guessInput.linesRead mustBe 2

      // It should have written the prompt text twice, and written the error message once
      guessInput.linesWritten.count(_ == guessInput.guessPromptMsg(suggestions)) mustBe 2
      guessInput.linesWritten.count(_ == guessInput.validationMsg) mustBe 1
    }
  }
}
