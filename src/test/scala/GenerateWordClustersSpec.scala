package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GenerateWordClustersSpec extends AnyFunSpec with Matchers {
  describe("Generate Word Clusters") {
    it("when all patterns are unique returns a set with entry for each") {
      val word = "ABCDE"
      val wordSet = Set("ABCDF", "EDCBA")
      val expectedResult = Set(
        List(Green, Green, Green, Green, Black),
        List(Yellow, Yellow, Green, Yellow, Yellow)
      )

      GenerateWordClusters(word, wordSet) must contain theSameElementsAs expectedResult
    }

    it("if multiple words have the same color patter it is only included once in set") {
      val word = "TRACE"
      val wordSet = Set("BRACE", "GRACE", "RACER", "BASIN")
      val expectedResult = Set(
        List(Black, Green, Green, Green, Green), // Brace and Grace have the same color pattern if trace is the answer
        List(Yellow, Yellow, Yellow, Yellow, Yellow), // Pattern for Racer
        List(Black, Yellow, Black, Black, Black)
      )

      GenerateWordClusters(word, wordSet) must contain theSameElementsAs expectedResult

    }
  }
}
