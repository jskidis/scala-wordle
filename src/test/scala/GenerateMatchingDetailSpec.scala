package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GenerateMatchingDetailSpec extends AnyFunSpec with Matchers {
  describe("Result To Pattern") {
    it("will return an empty matching pattern for empty words or color pattern") {
      val letters = List('A')
      val colors = List(Green)

      GenerateMatchingDetail("", Nil) mustBe MatchingDetail(Nil, Set())
      GenerateMatchingDetail("", colors) mustBe MatchingDetail(Nil, Set())
      GenerateMatchingDetail(letters.mkString, Nil) mustBe MatchingDetail(Nil, Set())
    }

    it("transforms green result") {
      val letters = "A"
      val colors = List(Green)
      val expectedResult = MatchingDetail(List(Option('A')), Set())

      GenerateMatchingDetail(letters, colors) mustBe expectedResult
    }

    it("transforms yellow result") {
      val letters = "A"
      val colors = List(Yellow)
      val expectedResult = MatchingDetail(List(None), Set('A'))

      GenerateMatchingDetail(letters, colors) mustBe expectedResult
    }

    it("transforms multi letter words") {
      val letters = "ABCDE"
      val colors = List(Green, Yellow, Black, Yellow, Green)
      val expectedResult = MatchingDetail(
        List(Option('A'), None, None, None, Option('E')),
        Set('B', 'D')
      )

      GenerateMatchingDetail(letters, colors) mustBe expectedResult
    }

    it("handles same yellow letter multiple times") {
      val letters = "ABCBE"
      val colors = List(Green, Yellow, Black, Yellow, Green)
      val expectedResult = MatchingDetail(
        List(Option('A'), None, None, None, Option('E')),
        Set('B')
      )

      GenerateMatchingDetail(letters, colors) mustBe expectedResult
    }
  }
}
