package com.skidis.wordle

import BlockColors.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GenerateClusterDefSpec extends AnyFunSpec with Matchers {
  describe("Result To Pattern") {
    it("will return an empty matching pattern for empty words or color pattern") {
      val letters = List('A')
      val colors = List(Green)

      GenerateClusterDef("", Nil) mustBe ClusterDef(Nil, Set())
      GenerateClusterDef("", colors) mustBe ClusterDef(Nil, Set())
      GenerateClusterDef(letters.mkString, Nil) mustBe ClusterDef(Nil, Set())
    }

    it("transforms green result") {
      val letters = "A"
      val colors = List(Green)
      val expectedResult = ClusterDef(List(Option('A')), Set())

      GenerateClusterDef(letters, colors) mustBe expectedResult
    }

    it("transforms yellow result") {
      val letters = "A"
      val colors = List(Yellow)
      val expectedResult = ClusterDef(List(None), Set('A'))

      GenerateClusterDef(letters, colors) mustBe expectedResult
    }

    it("transforms multi letter words") {
      val letters = "ABCDE"
      val colors = List(Green, Yellow, Black, Yellow, Green)
      val expectedResult = ClusterDef(
        List(Option('A'), None, None, None, Option('E')),
        Set('B', 'D')
      )

      GenerateClusterDef(letters, colors) mustBe expectedResult
    }

    it("handles same yellow letter multiple times") {
      val letters = "ABCBE"
      val colors = List(Green, Yellow, Black, Yellow, Green)
      val expectedResult = ClusterDef(
        List(Option('A'), None, None, None, Option('E')),
        Set('B')
      )

      GenerateClusterDef(letters, colors) mustBe expectedResult
    }
  }
}
