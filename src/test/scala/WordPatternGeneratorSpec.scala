package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordPatternGeneratorSpec extends AnyFunSpec with Matchers {
  describe("Word Matches Matching Detail") {
    it("returns true if the word matches green positions") {
      val wordPattern = List(('T', Green), ('R', Green), ('A', Black), ('C', Black), ('E', Green))

      WordPatternGenerator.generateFromWord("TRIBE", wordPattern) mustBe true
      WordPatternGenerator.generateFromWord("TROVE", wordPattern) mustBe true
      WordPatternGenerator.generateFromWord("GRACE", wordPattern) mustBe false // doesn't have T at first position
      WordPatternGenerator.generateFromWord("TULLE", wordPattern) mustBe false // doesn't have R at second position
      WordPatternGenerator.generateFromWord("TROLL", wordPattern) mustBe false // doesn't have E at fifth position
      WordPatternGenerator.generateFromWord("SUNNY", wordPattern) mustBe false // doesn't have any letters at correct position
    }

    it("return true if the word contains yellow letters but not at the yellow position") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Yellow), ('C', Yellow), ('E', Black))

      WordPatternGenerator.generateFromWord("SCUBA", wordPattern) mustBe true
      WordPatternGenerator.generateFromWord("BACON", wordPattern) mustBe true
      WordPatternGenerator.generateFromWord("SONAR", wordPattern) mustBe false // no A, but has C
      WordPatternGenerator.generateFromWord("SONIC", wordPattern) mustBe false // no C, but has A
      WordPatternGenerator.generateFromWord("SORRY", wordPattern) mustBe false // no A or C
      WordPatternGenerator.generateFromWord("SCALY", wordPattern) mustBe false // has A and C, but A is in the yellow position
    }

    it("return false if letter at yellow position is the yellow letter (should be green") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Yellow), ('C', Yellow), ('E', Black))

      WordPatternGenerator.generateFromWord("SCARF", wordPattern) mustBe false // contains C & A but A is in the yellow position
      WordPatternGenerator.generateFromWord("SAUCY", wordPattern) mustBe false // contains C & A but C in the yellow position
    }

    it("return false if the word contains any black letters, but letter is not a green or yellow letter as well") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Black), ('C', Black), ('E', Black))

      WordPatternGenerator.generateFromWord("SHINY", wordPattern) mustBe true
      WordPatternGenerator.generateFromWord("SALAD", wordPattern) mustBe false // contains A
      WordPatternGenerator.generateFromWord("SEEDY", wordPattern) mustBe false // contains E
      WordPatternGenerator.generateFromWord("SCOUT", wordPattern) mustBe false // contains T and C
    }

    it("puts it all together") {
      val wordPattern = List(('T', Green), ('R', Black), ('A', Yellow), ('C', Black), ('E', Green))

      WordPatternGenerator.generateFromWord("TRACE", wordPattern) mustBe false // guessed word should not match, since it would have been all green
      WordPatternGenerator.generateFromWord("TABLE", wordPattern) mustBe true // has T & E at green, has A but not at yellow, does not have R or C
      WordPatternGenerator.generateFromWord("TACKY", wordPattern) mustBe false // doesn't have E at 5th position
      WordPatternGenerator.generateFromWord("ABIDE", wordPattern) mustBe false // doesn't have E at 1st position
      WordPatternGenerator.generateFromWord("TENSE", wordPattern) mustBe false // doesn't have an A
      WordPatternGenerator.generateFromWord("TEASE", wordPattern) mustBe false // does have an A but it's in the position as Yellow A
      WordPatternGenerator.generateFromWord("TRADE", wordPattern) mustBe false // has an R which is a black letter
    }

    it("handles multiple letter cases") {
      // There's one Green A and one Black A
      val wordPattern1G1B = List(('A', Black), ('A', Green), ('X', Black), ('Y', Black), ('Z', Black))
      WordPatternGenerator.generateFromWord("BATUV", wordPattern1G1B) mustBe true
      WordPatternGenerator.generateFromWord("BAATU", wordPattern1G1B) mustBe false

      // There's two Green A's and one Black A
      val wordPattern2G1B = List(('A', Green), ('X', Black), ('A', Black), ('Y', Black), ('A', Green))
      WordPatternGenerator.generateFromWord("ATUVA", wordPattern2G1B) mustBe true
      WordPatternGenerator.generateFromWord("AATUA", wordPattern2G1B) mustBe false

      // There's one Yellow A and one Black A
      val wordPattern1Y1B = List(('A', Yellow), ('A', Black), ('X', Black), ('Y', Black), ('Z', Black))
      WordPatternGenerator.generateFromWord("TUVAB", wordPattern1Y1B) mustBe true
      WordPatternGenerator.generateFromWord("TUVAA", wordPattern1Y1B) mustBe false

      // There's two Yellow A's and one Black A
      val wordPattern2Y1B = List(('A', Yellow), ('X', Black), ('A', Yellow), ('Y', Black), ('A', Black))
      WordPatternGenerator.generateFromWord("TAUAV", wordPattern2Y1B) mustBe true
      WordPatternGenerator.generateFromWord("TAUAA", wordPattern2Y1B) mustBe false

      // There's one Green A, one Yellow A and one Black A
      val wordPattern1G1Y1B = List(('A', Yellow), ('X', Black), ('A', Black), ('Y', Black), ('A', Green))
      WordPatternGenerator.generateFromWord("TAUVA", wordPattern1G1Y1B) mustBe true
      WordPatternGenerator.generateFromWord("TAUAA", wordPattern1G1Y1B) mustBe false
    }
  }
}
