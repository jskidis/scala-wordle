package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordPatternMatcherSpec extends AnyFunSpec with Matchers {
  describe("Word Matches Matching Detail") {
    it("returns true if the word matches AInPosHint positions") {
      val wordPattern = Seq(('T', AInPosHint), ('R', AInPosHint), ('A', AMissHint), ('C', AMissHint), ('E', AInPosHint))

      WordPatternMatcher.doesWordMatch("TRIBE", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("TROVE", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("GRACE", wordPattern) mustBe false // doesn't have T at first position
      WordPatternMatcher.doesWordMatch("TULLE", wordPattern) mustBe false // doesn't have R at second position
      WordPatternMatcher.doesWordMatch("TROLL", wordPattern) mustBe false // doesn't have E at fifth position
      WordPatternMatcher.doesWordMatch("SUNNY", wordPattern) mustBe false // doesn't have any letters at correct position
    }

    it("return true if the word contains AInWordHint letters but not at the AInWordHint position") {
      val wordPattern = Seq(('T', AMissHint), ('R', AMissHint), ('A', AInWordHint), ('C', AInWordHint), ('E', AMissHint))

      WordPatternMatcher.doesWordMatch("SCUBA", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("BACON", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("SONAR", wordPattern) mustBe false // no A, but has C
      WordPatternMatcher.doesWordMatch("SONIC", wordPattern) mustBe false // no C, but has A
      WordPatternMatcher.doesWordMatch("SORRY", wordPattern) mustBe false // no A or C
      WordPatternMatcher.doesWordMatch("SCALY", wordPattern) mustBe false // has A and C, but A is in the AInWordHint position
    }

    it("return false if letter at AInWordHint position is the AInWordHint letter (should be AInPosHint") {
      val wordPattern = Seq(('T', AMissHint), ('R', AMissHint), ('A', AInWordHint), ('C', AInWordHint), ('E', AMissHint))

      WordPatternMatcher.doesWordMatch("SCARF", wordPattern) mustBe false // contains C & A but A is in the AInWordHint position
      WordPatternMatcher.doesWordMatch("SAUCY", wordPattern) mustBe false // contains C & A but C in the AInWordHint position
    }

    it("return false if the word contains any black letters, but letter is not a AInPosHint or AInWordHint letter as well") {
      val wordPattern = Seq(('T', AMissHint), ('R', AMissHint), ('A', AMissHint), ('C', AMissHint), ('E', AMissHint))

      WordPatternMatcher.doesWordMatch("SHINY", wordPattern) mustBe true
      WordPatternMatcher.doesWordMatch("SALAD", wordPattern) mustBe false // contains A
      WordPatternMatcher.doesWordMatch("SEEDY", wordPattern) mustBe false // contains E
      WordPatternMatcher.doesWordMatch("SCOUT", wordPattern) mustBe false // contains T and C
    }

    it("puts it all together") {
      val wordPattern = Seq(('T', AInPosHint), ('R', AMissHint), ('A', AInWordHint), ('C', AMissHint), ('E', AInPosHint))

      WordPatternMatcher.doesWordMatch("TRACE", wordPattern) mustBe false // guessed word should not match, since it would have been all AInPosHint
      WordPatternMatcher.doesWordMatch("TABLE", wordPattern) mustBe true // has T & E at AInPosHint, has A but not at AInWordHint, does not have R or C
      WordPatternMatcher.doesWordMatch("TACKY", wordPattern) mustBe false // doesn't have E at 5th position
      WordPatternMatcher.doesWordMatch("ABIDE", wordPattern) mustBe false // doesn't have E at 1st position
      WordPatternMatcher.doesWordMatch("TENSE", wordPattern) mustBe false // doesn't have an A
      WordPatternMatcher.doesWordMatch("TEASE", wordPattern) mustBe false // does have an A but it's in the position as AInWordHint A
      WordPatternMatcher.doesWordMatch("TRADE", wordPattern) mustBe false // has an R which is a black letter
    }

    it("handles multiple letter cases") {
      // There's one in-position A and one miss A
      val wordPattern1G1B = Seq(('A', AMissHint), ('A', AInPosHint), ('X', AMissHint), ('Y', AMissHint), ('Z', AMissHint))
      WordPatternMatcher.doesWordMatch("BATUV", wordPattern1G1B) mustBe true
      WordPatternMatcher.doesWordMatch("BAATU", wordPattern1G1B) mustBe false

      // There's two in-position A's and one miss A
      val wordPattern2G1B = Seq(('A', AInPosHint), ('X', AMissHint), ('A', AMissHint), ('Y', AMissHint), ('A', AInPosHint))
      WordPatternMatcher.doesWordMatch("ATUVA", wordPattern2G1B) mustBe true
      WordPatternMatcher.doesWordMatch("AATUA", wordPattern2G1B) mustBe false

      // There's one in-word A and one miss A
      val wordPattern1Y1B = Seq(('A', AInWordHint), ('A', AMissHint), ('X', AMissHint), ('Y', AMissHint), ('Z', AMissHint))
      WordPatternMatcher.doesWordMatch("TUVAB", wordPattern1Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TUVAA", wordPattern1Y1B) mustBe false

      // There's two in-word A's and one miss A
      val wordPattern2Y1B = Seq(('A', AInWordHint), ('X', AMissHint), ('A', AInWordHint), ('Y', AMissHint), ('A', AMissHint))
      WordPatternMatcher.doesWordMatch("TAUAV", wordPattern2Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TAUAA", wordPattern2Y1B) mustBe false

      // There's one in-position A, one in-word A and one miss A
      val wordPattern1G1Y1B = Seq(('A', AInWordHint), ('X', AMissHint), ('A', AMissHint), ('Y', AMissHint), ('A', AInPosHint))
      WordPatternMatcher.doesWordMatch("TAUVA", wordPattern1G1Y1B) mustBe true
      WordPatternMatcher.doesWordMatch("TAUAA", wordPattern1G1Y1B) mustBe false
    }
  }
}
