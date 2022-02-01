package com.skidis.wordle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.io.Source

class WordReaderSpec extends AnyFunSpec with Matchers {
  describe("Word Reader") {
    it("reads in words from file") {
      val expectedWords = Set("HELLO", "NURSE", "THATS", "ALLLL", "FOLKS")
      val results = WordReader.read(Source.fromResource("testWords.txt"))
      results must contain theSameElementsAs expectedWords
    }

    it("converts all words to lower case") {
      val expectedWords = Set("HELLO", "NURSE", "THATS", "ALLLL", "FOLKS")
      val results = WordReader.read(Source.fromResource("mixedCaseWords.txt"))
      results must contain theSameElementsAs expectedWords
    }
  }
}
