package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordClusterGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Generate Word Clusters") {
    it("when all patterns are unique returns a set with entry for each") {
      val word = "ABCDE"
      val wordSet = Set(
        "ABCDF", // Green, Green, Green, Green, Black
        "EDCBA" // Yellow, Yellow, Green, Yellow, Yellow
      )
      // 2 unique word clusters
      WordClusterGenerator.generateUnique(word, wordSet) mustBe 2
    }

    it("if multiple words have the same color patter it is only included once in set") {
      val word = "TRACE"
      val wordSet = Set(
        "BRACE", // Black, Green, Green, Green, Green
        "GRACE", // Black, Green, Green, Green, Green (Brace and Grace in same cluster)
        "RACER", // Yellow, Yellow, Yellow, Yellow, Black
        "BASIN"  // Black, Yellow, Black, Black, Black
      )
      // 3 unique word clusters
      WordClusterGenerator.generateUnique(word, wordSet) mustBe 3
    }
  }
}
