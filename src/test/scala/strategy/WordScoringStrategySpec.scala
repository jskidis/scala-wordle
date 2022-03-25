package com.skidis.wordle
package strategy

import TestFixtures.TWord

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class WordScoringStrategySpec extends AnyFunSpec with Matchers {

  object StrategyFixture extends WordScoringStrategy with SolveStrategyWithReduceWordSetFixture {
    // Just give a score based on length of word, it should sort longer words first
    def scoreWord(word: XrdleWord): Double = {
      word.text.length
    }
    override def scoreWordFunction(wordSet: WordSet): XrdleWord => Double = scoreWord
  }

  describe("Word Scoring Strategy") {
    it("reverses sorts the suggestions for the next guess") {
      val words  = (1 to 10).map { i => Seq.fill(i)('A').mkString}
      val wordSet = Random.shuffle(words.map(TWord)).toSet

      val results = StrategyFixture.generateNextGuesses(wordSet, previousGuesses = Nil, numToReturn = 3)
      results mustBe Vector("AAAAAAAAAA", "AAAAAAAAA", "AAAAAAAA")
    }
  }
}
