package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class ResultInputSpec extends AnyFunSpec with Matchers {
  val validInput:String = List(greenChar, yellowChar, blackChar, greenChar, yellowChar).mkString
  val validInputColors = List(Green, Yellow, Black, Green, Yellow)

  class TestResultInput(inputs: List[String]) extends ResultInput {
    var linesRead = 0
    var lineWritten = new ListBuffer[String]()

    override def readLine(): String = {
      linesRead = linesRead +1
      inputs(linesRead -1)
    }

    override def writeLine(s: String): Unit = lineWritten.addOne(s)
    override def writeString(s: String): Unit = lineWritten.addOne(s)
    override def validateResult(input: String): Boolean = input == validInput
  }

  describe("Gather Results") {
    it("returns results from reader when valid result is entered on first try") {
      val resultInput = new TestResultInput(List(validInput))
      val result = resultInput.generatePattern()

      // It should return a result and that result should be equal to "validResult" value
      result must not be empty
      result mustBe validInputColors

      resultInput.lineWritten must have size 1
      resultInput.lineWritten.head mustBe resultInput.resultPrompt
    }

    it("re-asks for results if not valid") {
      val resultInput = new TestResultInput(List("x", validInput))
      val result = resultInput.generatePattern()

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result must not be empty
      result mustBe validInputColors

      // It should have called the line reader 2 times
      resultInput.linesRead mustBe 2

      // It should have written the prompt text twice, and written the error message once
      resultInput.lineWritten.count(_ == resultInput.resultPrompt) mustBe 2
      resultInput.lineWritten.count(_ == resultInput.resultErrorMsg) mustBe 1
    }

    it("returns None if input is blank") {
      val resultInput = new TestResultInput(List("g", "b", "", validInput)) // ignores last line (validResult) because empty line stopped it
      val result = resultInput.generatePattern()

      // The result should be empty because it encountered an empty line before a valid value
      result mustBe empty

      // It should have called the line reader 3 times
      resultInput.linesRead mustBe 3

      // It should have written the prompt text 3 times, and written the error message twice
      resultInput.lineWritten.count(_ == resultInput.resultPrompt) mustBe 3
      resultInput.lineWritten.count(_ == resultInput.resultErrorMsg) mustBe 2
    }
  }
}
