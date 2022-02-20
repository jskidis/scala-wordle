package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class GuessInputSpec extends AnyFunSpec with Matchers {
  val suggestion: String = "guess"
  val validInput:String = "abcde"

  class TestGuessInput(inputs: List[String]) extends GuessInput {
    var linesRead = 0
    var linesWritten = new ListBuffer[String]()

    override def readLine(): String = {
      linesRead = linesRead +1
      inputs(linesRead -1)
    }

    override def writeLine(s: String): Unit = {
      linesWritten.addOne(s)
    }

    override def validateGuess(s: String): Boolean = {
      s == validInput.toUpperCase()
    }
  }


  describe("Gather Guess") {
    it("returns the suggested word if the input is empty") {
      val guessInput = new TestGuessInput(List(""))
      val result = guessInput.getGuessFromInput(suggestion)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestion.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestion)
    }

    it("returns the suggested word if the trim of input is empty") {
      val guessInput = new TestGuessInput(List("  "))
      val result = guessInput.getGuessFromInput(suggestion)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestion.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestion)
    }

    it("returns results from reader when valid result is entered on first try") {
      val guessInput = new TestGuessInput(List(validInput))
      val result = guessInput.getGuessFromInput(suggestion)

      // It should return a result and that result should be equal to "validResult" value
      result mustBe validInput.toUpperCase

      guessInput.linesWritten must have size 1
      guessInput.linesWritten.head mustBe guessInput.guessPromptMsg(suggestion)
    }

    it("re-asks for results if not valid") {
      val guessInput = new TestGuessInput(List("12345", validInput))
      val result = guessInput.getGuessFromInput(suggestion)

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result mustBe validInput.toUpperCase

      // It should have called the line reader 2 times
      guessInput.linesRead mustBe 2

      // It should have written the prompt text twice, and written the error message once
      guessInput.linesWritten.count(_ == guessInput.guessPromptMsg(suggestion)) mustBe 2
      guessInput.linesWritten.count(_ == guessInput.guessErrorMsg) mustBe 1
    }
  }
}

