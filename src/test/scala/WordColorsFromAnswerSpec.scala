package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordColorsFromAnswerSpec extends AnyFunSpec with Matchers {
  describe("Word Colors From (Potential) Answer") {
    it("identifies green letters") {
      val answer = "TRACE"
      WordColorsFromAnswer(answer, "THANK") mustBe List(Green, Black, Green, Black, Black)
      WordColorsFromAnswer(answer, "GRACE") mustBe List(Black, Green, Green, Green, Green)
    }

    it("identifies yellow letters") {
      val answer = "TRACE"
      WordColorsFromAnswer(answer, "ABBEY") mustBe List(Yellow, Black, Black, Yellow, Black)
      WordColorsFromAnswer(answer, "RACER") mustBe List(Yellow, Yellow, Yellow, Yellow, Yellow)
    }

    it("identifies green and yellow letters") {
      val answer = "TRACE"
      WordColorsFromAnswer(answer, "ABBEY") mustBe List(Yellow, Black, Black, Yellow, Black)
      WordColorsFromAnswer(answer, "TREAT") mustBe List(Green, Green, Yellow, Yellow, Yellow)
    }
  }
}
