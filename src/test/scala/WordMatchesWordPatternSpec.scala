package com.skidis.wordle

import BlockColor.{Black, Green, Yellow}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordMatchesWordPatternSpec extends AnyFunSpec with Matchers {
  describe("Word Matches Matching Detail") {
    it("returns true if the word matches green positions") {
      val wordPattern = List(('T', Green), ('R', Green), ('A', Black), ('C', Black), ('E', Green))

      WordMatchesWordPattern("TRIBE", wordPattern) mustBe true
      WordMatchesWordPattern("TROVE", wordPattern) mustBe true
      WordMatchesWordPattern("GRACE", wordPattern) mustBe false // doesn't have T at first position
      WordMatchesWordPattern("TULLE", wordPattern) mustBe false // doesn't have R at second position
      WordMatchesWordPattern("TROLL", wordPattern) mustBe false // doesn't have E at fifth position
      WordMatchesWordPattern("SUNNY", wordPattern) mustBe false // doesn't have any letters at correct position
    }

    it("return true if the word contains yellow letters but not at the yellow position") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Yellow), ('C', Yellow), ('E', Black))

      WordMatchesWordPattern("SCUBA", wordPattern) mustBe true
      WordMatchesWordPattern("BACON", wordPattern) mustBe true
      WordMatchesWordPattern("SONAR", wordPattern) mustBe false // no A, but has C
      WordMatchesWordPattern("SONIC", wordPattern) mustBe false // no C, but has A
      WordMatchesWordPattern("SORRY", wordPattern) mustBe false // no A or C
      WordMatchesWordPattern("SCALY", wordPattern) mustBe false // has A and C, but A is in the yellow position
    }

    it("return false if letter at yellow position is the yellow letter (should be green") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Yellow), ('C', Yellow), ('E', Black))

      WordMatchesWordPattern("SCARF", wordPattern) mustBe false // contains C & A but A is in the yellow position
      WordMatchesWordPattern("SAUCY", wordPattern) mustBe false // contains C & A but C in the yellow position
    }

    it("return false if the word contains any black letters") {
      val wordPattern = List(('T', Black), ('R', Black), ('A', Black), ('C', Black), ('E', Black))

      WordMatchesWordPattern("SHINY", wordPattern) mustBe true
      WordMatchesWordPattern("SALAD", wordPattern) mustBe false // contains A
      WordMatchesWordPattern("SEEDY", wordPattern) mustBe false // contains E
      WordMatchesWordPattern("SCOUT", wordPattern) mustBe false // contains T and C
    }

    it("puts it all together") {
      val wordPattern = List(('T', Green), ('R', Black), ('A', Yellow), ('C', Black), ('E', Green))

      WordMatchesWordPattern("TRACE", wordPattern) mustBe false // guessed word should not match, since it would have been all green
      WordMatchesWordPattern("TABLE", wordPattern) mustBe true // has T & E at green, has A but not at yellow, does not have R or C
      WordMatchesWordPattern("TACKY", wordPattern) mustBe false // doesn't have E at 5th position
      WordMatchesWordPattern("ABIDE", wordPattern) mustBe false // doesn't have E at 1st position
      WordMatchesWordPattern("TENSE", wordPattern) mustBe false // doesn't have an A
      WordMatchesWordPattern("TEASE", wordPattern) mustBe false // does have an A but it's in the position as Yellow A
      WordMatchesWordPattern("TRADE", wordPattern) mustBe false // has an R which is a black letter
    }

    it("matches on positions regardless of case") {
      val wordPattern = List(('T', Green), ('r', Black), ('a', Yellow), ('C', Black), ('e', Green))

      WordMatchesWordPattern("Trace", wordPattern) mustBe false // guessed word should not match, since it would have been all green
      WordMatchesWordPattern("table", wordPattern) mustBe true // has T & E at green, has A but not at yellow, does not have R or C
      WordMatchesWordPattern("tACKY", wordPattern) mustBe false // doesn't have E at 5th position
      WordMatchesWordPattern("ABide", wordPattern) mustBe false // doesn't have E at 1st position
      WordMatchesWordPattern("TeNsE", wordPattern) mustBe false // doesn't have an A
      WordMatchesWordPattern("tEaSe", wordPattern) mustBe false // does have an A but it's in the position as Yellow A
      WordMatchesWordPattern("tradE", wordPattern) mustBe false // has an R which is a black letter
    }
  }
}
