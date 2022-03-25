package com.skidis.wordle
package strategy

import TestFixtures.TWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class ReverseScoringStrategySpec extends AnyFunSpec with Matchers {

  object StrategyFixture extends ReverseScoringStrategy with SolveStrategyWithReduceWordSetFixture {
    // Just give a score based on length of word, w/o reverse it will sort longer words first
    def scoreWord(word: XrdleWord): Double = {
      word.text.length
    }
    override def scoreWordFunction(wordSet: WordSet): XrdleWord => Double = scoreWord
  }

  describe("Reverse Scoring Strategy") {
    it("reverses sorts the suggestions for the next guess") {
      val words  = (1 to 10).map { i => Seq.fill(i)('A').mkString}
      val wordSet = Random.shuffle(words.map(TWord)).toSet

      val results = StrategyFixture.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 3)
      results mustBe Vector("A", "AA", "AAA")
    }
  }
}
