package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class WordMatchesDetailSpec extends AnyFunSpec with Matchers {
  describe("Word Matches Matching Detail") {
    it("returns true if the word has same positional letters, false if not") {
      val matchingDetail = MatchingDetail(List(Option('H'), Option('E'), None, None, Option('O')), Set())

      WordMatchesDetail("HELLO", matchingDetail) mustBe true
      WordMatchesDetail("HELIO", matchingDetail) mustBe true
      WordMatchesDetail("JELLO", matchingDetail) mustBe false // doesn't have H at first position
      WordMatchesDetail("HILLO", matchingDetail) mustBe false // doesn't have E at second position
      WordMatchesDetail("HELLA", matchingDetail) mustBe false // doesn't have O at fifth position
      WordMatchesDetail("SMELL", matchingDetail) mustBe false // doesn't have any letters at correct position
    }

    it("return false if (non-positional) matching letters aren't found in word, true otherwise") {
      val matchingDetail = MatchingDetail(List.fill(5)(None), Set('L', 'O'))

      WordMatchesDetail("HELLO", matchingDetail) mustBe true
      WordMatchesDetail("JELLO", matchingDetail) mustBe true
      WordMatchesDetail("HEMOA", matchingDetail) mustBe false // no l, but has an o
      WordMatchesDetail("HELLA", matchingDetail) mustBe false // no o, but has an l
      WordMatchesDetail("SNOWY", matchingDetail) mustBe false // no l or o
    }

    it("handles both positional matches and other matches") {
      val matchingDetail = MatchingDetail(List(Option('H'), None, None, None, Option('O')), Set('E', 'L'))

      WordMatchesDetail("HELLO", matchingDetail) mustBe true
      WordMatchesDetail("HELIO", matchingDetail) mustBe true
      WordMatchesDetail("HELLA", matchingDetail) mustBe false // doesn't have O at 5th position
      WordMatchesDetail("JELLO", matchingDetail) mustBe false // doesn't have H at 1st position
      WordMatchesDetail("JELLA", matchingDetail) mustBe false // doesn't have any letters at correct position
      WordMatchesDetail("HESHO", matchingDetail) mustBe false // doesn't have an l
      WordMatchesDetail("HESHO", matchingDetail) mustBe false // doesn't have an e
      WordMatchesDetail("HICKO", matchingDetail) mustBe false // doesn't have an l or e
      WordMatchesDetail("SNOWY", matchingDetail) mustBe false // doesn't letters at correct position or an e or l
    }

    it("matches on positions regardless of case") {
      val matchingDetail = MatchingDetail(List(Option('h'), None, None, None, Option('O')), Set('l', 'e'))
      WordMatchesDetail("Hello", matchingDetail) mustBe true
    }
  }
}
