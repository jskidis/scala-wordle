package com.skidis.wordle

import BlockColor.{Blank, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordPatternMatcherSpec extends AnyFunSpec with Matchers {
  describe("Word Matches Matching Detail") {
    it("returns true if the word matches green positions") {
      val wordPattern = List(('T', Green), ('R', Green), ('A', Blank), ('C', Blank), ('E', Green))

      WordPatternMatcher.doesWordMatch("TRIBE", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("TROVE", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("GRACE", wordPattern) mustBe false // doesn't have T at first position
      WordPatternMatcher.doesWordMatch("TULLE", wordPattern) mustBe false // doesn't have R at second position
      WordPatternMatcher.doesWordMatch("TROLL", wordPattern) mustBe false // doesn't have E at fifth position
      WordPatternMatcher.doesWordMatch("SUNNY", wordPattern) mustBe false // doesn't have any letters at correct position
    }

    it("return true if the word contains yellow letters but not at the yellow position") {
      val wordPattern = List(('T', Blank), ('R', Blank), ('A', Yellow), ('C', Yellow), ('E', Blank))

      WordPatternMatcher.doesWordMatch("SCUBA", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("BACON", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("SONAR", wordPattern) mustBe false // no A, but has C
      WordPatternMatcher.doesWordMatch("SONIC", wordPattern) mustBe false // no C, but has A
      WordPatternMatcher.doesWordMatch("SORRY", wordPattern) mustBe false // no A or C
      WordPatternMatcher.doesWordMatch("SCALY", wordPattern) mustBe false // has A and C, but A is in the yellow position
    }

    it("return false if letter at yellow position is the yellow letter (should be green") {
      val wordPattern = List(('T', Blank), ('R', Blank), ('A', Yellow), ('C', Yellow), ('E', Blank))

      WordPatternMatcher.doesWordMatch("SCARF", wordPattern) mustBe false // contains C & A but A is in the yellow position
      WordPatternMatcher.doesWordMatch("SAUCY", wordPattern) mustBe false // contains C & A but C in the yellow position
    }

    it("return false if the word contains any black letters, but letter is not a green or yellow letter as well") {
      val wordPattern = List(('T', Blank), ('R', Blank), ('A', Blank), ('C', Blank), ('E', Blank))

      WordPatternMatcher.doesWordMatch("SHINY", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("SALAD", wordPattern) mustBe false // contains A
      WordPatternMatcher.doesWordMatch("SEEDY", wordPattern) mustBe false // contains E
      WordPatternMatcher.doesWordMatch("SCOUT", wordPattern) mustBe false // contains T and C
    }

    it("puts it all together") {
      val wordPattern = List(('T', Green), ('R', Blank), ('A', Yellow), ('C', Blank), ('E', Green))

      WordPatternMatcher.doesWordMatch("TRACE", wordPattern) mustBe false // guessed word should not match, since it would have been all green
      WordPatternMatcher.doesWordMatch("TABLE", wordPattern) mustBe true // has T & E at green, has A but not at yellow, does not have R or C
      WordPatternMatcher.doesWordMatch("TACKY", wordPattern) mustBe false // doesn't have E at 5th position
      WordPatternMatcher.doesWordMatch("ABIDE", wordPattern) mustBe false // doesn't have E at 1st position
      WordPatternMatcher.doesWordMatch("TENSE", wordPattern) mustBe false // doesn't have an A
      WordPatternMatcher.doesWordMatch("TEASE", wordPattern) mustBe false // does have an A but it's in the position as Yellow A
      WordPatternMatcher.doesWordMatch("TRADE", wordPattern) mustBe false // has an R which is a black letter
    }

    it("handles multiple letter cases") {
      // There's one Green A and one Black A
      val wordPattern1G1B = List(('A', Blank), ('A', Green), ('X', Blank), ('Y', Blank), ('Z', Blank))
      WordPatternMatcher.doesWordMatch("BATUV", wordPattern1G1B) mustBe true
      WordPatternMatcher.doesWordMatch("BAATU", wordPattern1G1B) mustBe false

      // There's two Green A's and one Black A
      val wordPattern2G1B = List(('A', Green), ('X', Blank), ('A', Blank), ('Y', Blank), ('A', Green))
      WordPatternMatcher.doesWordMatch("ATUVA", wordPattern2G1B) mustBe true
      WordPatternMatcher.doesWordMatch("AATUA", wordPattern2G1B) mustBe false

      // There's one Yellow A and one Black A
      val wordPattern1Y1B = List(('A', Yellow), ('A', Blank), ('X', Blank), ('Y', Blank), ('Z', Blank))
      WordPatternMatcher.doesWordMatch("TUVAB", wordPattern1Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TUVAA", wordPattern1Y1B) mustBe false

      // There's two Yellow A's and one Black A
      val wordPattern2Y1B = List(('A', Yellow), ('X', Blank), ('A', Yellow), ('Y', Blank), ('A', Blank))
      WordPatternMatcher.doesWordMatch("TAUAV", wordPattern2Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TAUAA", wordPattern2Y1B) mustBe false

      // There's one Green A, one Yellow A and one Black A
      val wordPattern1G1Y1B = List(('A', Yellow), ('X', Blank), ('A', Blank), ('Y', Blank), ('A', Green))
      WordPatternMatcher.doesWordMatch("TAUVA", wordPattern1G1Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TAUAA", wordPattern1G1Y1B) mustBe false
    }
  }
}
