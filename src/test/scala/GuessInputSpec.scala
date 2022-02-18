package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class GuessInputSpec extends AnyFunSpec with Matchers {
  val suggestion: String = "guess"
  val validInput:String = "abcde"

  class MockReader(inputs: List[String]) {
    var timesCalled = 0
    def readLine(): String = {
      timesCalled = timesCalled +1
      inputs(timesCalled -1)
    }
  }

  class MockWriter() {
    var lines = new ListBuffer[String]()
    def writeLine(l: String): Unit = {
      lines.addOne(l)
    }
  }

  def validator(s: String): Boolean = {
    s == validInput.toUpperCase()
  }

  describe("Gather Guess") {
    it("returns the suggested word if the input is empty") {
      val mockReader = new MockReader(List(""))
      val mockWriter = new MockWriter()
      val result = GuessInput.gatherGuess(mockReader.readLine, mockWriter.writeLine, validator)(suggestion)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestion.toUpperCase

      mockWriter.lines must have size 1
      mockWriter.lines.head mustBe GuessInput.promptMsg(suggestion)
    }

    it("returns the suggested word if the trim of input is empty") {
      val mockReader = new MockReader(List("  "))
      val mockWriter = new MockWriter()
      val result = GuessInput.gatherGuess(mockReader.readLine, mockWriter.writeLine, validator)(suggestion)

      // It should return a guess and that result should be equal to "validResult" value
      result mustBe suggestion.toUpperCase

      mockWriter.lines must have size 1
      mockWriter.lines.head mustBe GuessInput.promptMsg(suggestion)
    }

    it("returns results from reader when valid result is entered on first try") {
      val mockReader = new MockReader(List(validInput))
      val mockWriter = new MockWriter()
      val result = GuessInput.gatherGuess(mockReader.readLine, mockWriter.writeLine, validator)(suggestion)

      // It should return a result and that result should be equal to "validResult" value
      result mustBe validInput.toUpperCase

      mockWriter.lines must have size 1
      mockWriter.lines.head mustBe GuessInput.promptMsg(suggestion)
    }

    it("re-asks for results if not valid") {
      val mockReader = new MockReader(List("12345", validInput))
      val mockWriter = new MockWriter()
      val result = GuessInput.gatherGuess(mockReader.readLine, mockWriter.writeLine, validator)(suggestion)

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result mustBe validInput.toUpperCase

      // It should have called the line reader 2 times
      mockReader.timesCalled mustBe 2

      // It should have written the prompt text twice, and written the error message once
      mockWriter.lines.count(_ == GuessInput.promptMsg(suggestion)) mustBe 2
      mockWriter.lines.count(_ == GuessInput.errorMsg) mustBe 1
    }
  }
}

