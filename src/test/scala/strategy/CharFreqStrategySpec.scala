package com.skidis.wordle
package strategy

import TestFixtures.TWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class CharFreqStrategySpec extends AnyFunSpec with Matchers {

  describe("Char Frequency Scorer") {
    it("a word gets a point for the number of times each letter in a word is found in the set of words") {
      val wordSet = Set("ABC", "BAD", "AFE").map(TWord)

      // Should get 1 point for D and none for X and Y
      CharFreqScorer.scoreWord(TWord("CXY"), wordSet) mustBe 1

      // Should get 3 points for A, one point for E, and none for X
      CharFreqScorer.scoreWord(TWord("XEA"), wordSet) mustBe 4
    }

    it("a word also gets bonus points if letter is in same position of words in the word set") {
      val wordSet = Set("ABC", "BAD", "AFE").map(TWord)

      // Should get 1 point for E plus 5 for being in same position as AFE, 1 point for F, and none for X
      CharFreqScorer.scoreWord(TWord("FXE"), wordSet) mustBe 7

      // Should get 3 points for A, plus 5*2 for being in same position as ABC & AFE,
      // 2 points for B, plus 5 for being in same position as ABC and none for X
      CharFreqScorer.scoreWord(TWord("ABX"), wordSet) mustBe 20
    }

    it("any repeating letters in a word don't get any points regardless of char or in position frequency") {
      val wordSet = Set("ABC", "BAD", "AFE").map(TWord)

      // No points for either A, 1 point for D (matching BAD)
      CharFreqScorer.scoreWord(TWord("ADA"), wordSet) mustBe 1

      // No points for either A, 1 point for E plus bonus 5 for in position match (matching BAD)
      CharFreqScorer.scoreWord(TWord("BBE"), wordSet) mustBe 6
    }
  }

  val charFreqScoreMap: Map[String, Int] = Map(
    "SCORE-100" -> 100,
    "SCORE-50" -> 50,
    "SCORE-10" -> 10,
    "SCORE-5" -> 5,
    "SCORE-1" -> 1,
    "SCORE-0" -> 0
  )

  trait CharFreqScorerFixture extends CharFreqScorer {
    override def scoreWord(potentialAnswer: XrdleWord,
      charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int])
    : Int = charFreqScoreMap(potentialAnswer.text)
  }

  describe("Char Frequency Scorer") {
    it("returns the top words in terms of char frequency scores") {
      val wordSet = Random.shuffle(charFreqScoreMap.keySet.map(TWord))
      val strategy = new CharFreqStrategy with CharFreqScorerFixture

      val expectedResults = Vector("SCORE-100", "SCORE-50", "SCORE-10")
      val results = strategy.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 3)
      results mustBe expectedResults
    }
  }
}
