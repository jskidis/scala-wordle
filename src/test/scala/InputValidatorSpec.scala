package com.skidis.worlde

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InputValidatorSpec extends AnyFunSpec with Matchers with ResultChars {
  describe("Input Validator Spec") {
    it("returns false if input is not 5 chars in length") {
      InputValidator("a") mustBe false
      InputValidator("abcd") mustBe false
      InputValidator("abcdef") mustBe false
      InputValidator("abcdefghijklmnop") mustBe false
    }

    it("returns false if any of characters in the string are 'g', 'y', or 'b'") {
      InputValidator(List(matchInPos, matchNotInPos, noMatch, '.', ',').mkString) mustBe false
      InputValidator(List('.', ',', matchInPos, matchNotInPos, noMatch).mkString) mustBe false
      InputValidator(List(matchInPos, '.', matchNotInPos, ',', noMatch).mkString) mustBe false
      InputValidator(List(matchInPos, matchNotInPos, noMatch, matchInPos, matchNotInPos).mkString) mustBe true
    }

    it("ignores case for matching chars") {
      InputValidator(List(matchInPos.toUpper, matchNotInPos.toUpper, noMatch.toUpper, matchInPos.toUpper, matchNotInPos.toUpper).mkString) mustBe true
      InputValidator(List(matchInPos.toLower, matchNotInPos.toLower, noMatch.toLower, matchInPos.toLower, matchNotInPos.toLower).mkString) mustBe true
      InputValidator(List(matchInPos.toUpper, matchNotInPos.toLower, noMatch.toUpper, matchInPos.toLower, matchNotInPos.toUpper).mkString) mustBe true
    }
  }
}
