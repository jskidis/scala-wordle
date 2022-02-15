package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.io.Source

class WordReaderSpec extends AnyFunSpec with Matchers {
  describe("Word Reader, readWords") {
    it("reads in words from file") {
      val expectedWords = List("HELLO", "NURSE", "THATS", "ALLLL", "FOLKS").map(SimpleWordleWord)
      val results = WordReader.readWords(Source.fromResource("testWords.txt")).toList
      results must contain theSameElementsAs expectedWords
    }

    it("converts all words to upper case") {
      val expectedWords = List("HELLO", "NURSE", "THATS", "ALLLL", "FOLKS").map(SimpleWordleWord)
      val results = WordReader.readWords(Source.fromResource("mixedCaseWords.txt")).toList
      results must contain theSameElementsAs expectedWords
    }
  }

  describe("Word Reader, readFrequencies") {
    it("reads in words and frequencies from file") {
      val expectedWords = List(
        WordleWordFrequencies("HELLO", 1.01E-1),
        WordleWordFrequencies("NURSE", 2.02E-2),
        WordleWordFrequencies("THATS", 3.03E-3),
        WordleWordFrequencies("ALLLL", 4.04E-4),
        WordleWordFrequencies("FOLKS", 5.05E-5)
      )
      val results = WordReader.readWordFrequencies(Source.fromResource("testWordFrequencies.txt")).toList
      results must contain theSameElementsAs expectedWords
    }

    it("ignores any white space before and after comma") {
      val expectedWords = List(
        WordleWordFrequencies("HELLO", 1.01E-1),
        WordleWordFrequencies("NURSE", 2.02E-2),
        WordleWordFrequencies("THATS", 3.03E-3),
        WordleWordFrequencies("ALLLL", 4.04E-4),
        WordleWordFrequencies("FOLKS", 5.05E-5)
      )
      val results = WordReader.readWordFrequencies(Source.fromResource("testWordFrequenciesExtraSpace.txt")).toList
      results must contain theSameElementsAs expectedWords
    }
  }
}
