package com.skidis.wordle

import BlockColors.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.collection.mutable.ListBuffer

class GatherResultSpec extends AnyFunSpec with Matchers {
  val validInput:String = List(greenChar, yellowChar, blackChar, greenChar, yellowChar).mkString
  val validInputColors = List(Green, Yellow, Black, Green, Yellow)

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

  def validator(s: String): Boolean = s == validInput

  describe("Gather Results") {
    it("returns results from reader when valid result is entered on first try") {
      val mockReader = new MockReader(List(validInput))
      val mockWriter = new MockWriter()
      val result = GatherResult(mockReader.readLine, mockWriter.writeLine, validator)

      // It should return a result and that result should be equal to "validResult" value
      result must not be empty
      result mustBe validInputColors

      mockWriter.lines must have size 1
      mockWriter.lines.head mustBe GatherResult.PromptMsg
    }

    it("re-asks for results if not valid") {
      val mockReader = new MockReader(List("x", validInput))
      val mockWriter = new MockWriter()
      val result = GatherResult(mockReader.readLine, mockWriter.writeLine, validator)

      // It should return a result and that result should be equal to "validResult" value, it should have c
      result must not be empty
      result mustBe validInputColors

      // It should have called the line reader 2 times
      mockReader.timesCalled mustBe 2

      // It should have written the prompt text twice, and written the error message once
      mockWriter.lines.count(_ == GatherResult.PromptMsg) mustBe 2
      mockWriter.lines.count(_ == GatherResult.ErrorMsg) mustBe 1
    }

    it("returns None if input is blank") {
      val mockReader = new MockReader(List("g", "b", "", validInput)) // ignores last line (validResult) because empty line stopped it
      val mockWriter = new MockWriter()
      val result = GatherResult(mockReader.readLine, mockWriter.writeLine, validator)

      // The result should be empty because it encountered an empty line before a valid value
      result mustBe empty

      // It should have called the line reader 3 times
      mockReader.timesCalled mustBe 3

      // It should have written the prompt text 3 times, and written the error message twice
      mockWriter.lines.count(_ == GatherResult.PromptMsg) mustBe 3
      mockWriter.lines.count(_ == GatherResult.ErrorMsg) mustBe 2
    }
  }
}
