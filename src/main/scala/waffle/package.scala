package com.skidis.wordle

import wordle.{WordleGuessProps, WordleHintProps}

package object waffle {
  type CharFreqMap = Map[Char, Int]

  trait WaffleHintProps extends WordleHintProps
  trait WaffleGuessProps extends WordleGuessProps
  case class WaffleWord(txt: String) extends XrdleWord {
    override def text: String = txt
  }

  case class WaffleWordDetail(word: XrdleWord, hints:WordHints, wordSet: WordSet) {
    def replaceWord(newWord: XrdleWord): WaffleWordDetail = {
      WaffleWordDetail(newWord, hints, wordSet)
    }
    def replaceWordSet(newWordSet: WordSet): WaffleWordDetail = {
      WaffleWordDetail(word, hints, newWordSet)
    }
  }

  case class WaffleDetailGrid(wordDetails: Seq[WaffleWordDetail]) {
    def apply(): Seq[WaffleWordDetail] = wordDetails
    def apply(number: Int): WaffleWordDetail = wordDetails(number)
    def wordGrid: WaffleWordGrid = WaffleWordGrid(wordDetails.map(_.word))
    def hintGrid: Seq[WordHints] = wordDetails.map(_.hints)
    def rotateGrid(): WaffleDetailGrid = {
      WaffleDetailGrid(Seq(wordDetails(3), wordDetails(4), wordDetails(5), wordDetails(0), wordDetails(1), wordDetails(2)))
    }
    def hasSolution: Boolean = wordDetails.forall{ wwd =>
      wwd.wordSet.size == 1
    }
    def convertToSolution(): WaffleDetailGrid = WaffleDetailGrid(wordDetails.map { wwd =>
      if (wwd.wordSet.size != 1) wwd else wwd.replaceWord(wwd.wordSet.head)
    })
  }

  case class WaffleWordGrid(words: Seq[XrdleWord]) {
    def apply(): Seq[XrdleWord] = words
    def apply(number: Int): XrdleWord = words(number)
    def gridLetters: Seq[Char] = {
      words(0).text + words(1).text + words(2).text + Seq(
        words(3).text(1), words(3).text(3),
        words(4).text(1), words(4).text(3),
        words(5).text(1), words(5).text(3)
      ).mkString("")
    }
  }
}
