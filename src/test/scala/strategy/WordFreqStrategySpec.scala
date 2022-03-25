package com.skidis.wordle
package strategy

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class WordFreqStrategySpec extends AnyFunSpec with Matchers {
  case class FreqWord(text: String, frequency: Double) extends XrdleFreqWord

  describe("Word Frequency Scoring Strategy") {
    it("reverses sorts the suggestions for the next guess") {
      // Randomizing text
      val words  = (0 to 9).reverse.map { i => FreqWord(Random.nextPrintableChar().toString, i.toDouble) }
      val wordSet = Random.shuffle(words).toSet

      val results = WordFreqStrategy.generateNextGuesses(wordSet, previousGuesses = Nil, 3)
      results mustBe Vector(words(0).text, words(1).text, words(2).text)
    }
  }
}
