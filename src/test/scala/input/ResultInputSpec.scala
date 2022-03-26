package com.skidis.wordle
package input

import TestFixtures._

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ResultInputSpec extends AnyFunSpec with Matchers {
  val validInput: String = Seq(inPosChar, inWordChar, missChar, inPosChar, inWordChar).mkString
  val validInputColors = Seq(TInPosHint, TInWordHint, TMissHint, TInPosHint, TInWordHint)

  class TestBasicResultInput(inputs: Vector[String]) extends ResultInput with TestHintProps with WriterToBuffer {
    var linesRead = 0

    override def readLine(): String = {
      linesRead = linesRead +1
      inputs(linesRead -1)
    }

    def errorMsg = "Input invalid"
    override def validateResult(input: String): Option[String] = {
      if (input == validInput) None
      else Some(errorMsg)
    }
  }

  describe("Gather Results") {
    it("returns results from reader when valid result is entered on first try") {
      val resultInput = new TestBasicResultInput(Vector(validInput))
      val result = resultInput.generatePattern()

      // It should return a result and that result should be equal to "validResult" value
      result must not be empty
      result mustBe validInputColors

      resultInput.linesWritten must have size 1
      resultInput.linesWritten.head mustBe resultInput.resultPrompt
    }

    it("re-asks for results if not valid") {
      val resultInput = new TestBasicResultInput(Vector("x", validInput))
      val result = resultInput.generatePattern()

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result must not be empty
      result mustBe validInputColors

      // It should have called the line reader 2 times
      resultInput.linesRead mustBe 2

      // It should have written the prompt text twice, and written the error message once
      resultInput.linesWritten.count(_ == resultInput.resultPrompt) mustBe 2
      resultInput.linesWritten.count(_ == resultInput.errorMsg) mustBe 1
    }

    it("returns None if input is blank") {
      val resultInput = new TestBasicResultInput(Vector("g", "b", "", validInput)) // ignores last line (validResult) because empty line stopped it
      val result = resultInput.generatePattern()

      // The result should be empty because it encountered an empty line before a valid value
      result mustBe empty

      // It should have called the line reader 3 times
      resultInput.linesRead mustBe 3

      // It should have written the prompt text 3 times, and written the error message twice
      resultInput.linesWritten.count(_ == resultInput.resultPrompt) mustBe 3
      resultInput.linesWritten.count(_ == resultInput.errorMsg) mustBe 2
    }
  }
}
