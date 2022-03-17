package com.skidis.wordle
package strategy

trait CharFreqStrategy extends StandardWordElimStrategy with WordScoring {
  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[(String, WordHints)], numToReturn: Int)
  : Seq[String] = {
    val words = remainingWords.map{l: XordlePhrase => l.phrase}.toSeq
    val charFreqMap = createCharFreqMap(words)
    val charIdxFreqMap = createCharIndexFreqMap(words)

    scoreAndSortWords(scoreWord(charFreqMap, charIdxFreqMap))(remainingWords).take(numToReturn)
  }

  def scoreWord(charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int])
    (potentialAnswer: XordlePhrase, remainingWords: WordSet)
  : Double = {
    val rc = repeatingChars(potentialAnswer.phrase)
    val letterScore = potentialAnswer.phrase.zipWithIndex.map{ case (ch: Char, idx: Int) =>
      if (rc.contains(ch)) 0
      else charFreqMap.getOrElse(ch, 0) + 5 * charIdxFreqMap.getOrElse((ch, idx), 0)
    }.sum
    potentialAnswer match {
      case wf: XordlePhaseFreq => Math.log10(letterScore) + Math.log10(wf.frequency)*2
    }

  }

  def repeatingChars(word: String): Seq[Char] = {
    word.groupBy(ch => ch).filter {
      case (_: Char, seq: String) => seq.length > 1
    }.keys.toSeq
  }

  def createCharIndexFreqMap(words: Seq[String]): Map[(Char, Int), Int] = {
    words.flatMap{ word =>
      word.zipWithIndex
    }.groupBy { case (ch: Char, idx: Int) =>
      (ch, idx)
    }.map { case ((ch: Char, idx: Int), seq: Seq[(Char, Int)]) =>
      ((ch, idx), seq.size)
    }
  }

  def createCharFreqMap(words: Seq[String]): Map[Char, Int] = {
    words.flatten.groupBy(c=>c).map {
      case (ch: Char, seq: Seq[Char]) => (ch, seq.size)
    }
  }
}


