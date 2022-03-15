package com.skidis.wordle
package strategy

trait CharFreqStrategy extends StandardWordElimStrategy {
  override def generateNextGuesses(remainingWords: WordSet, previousGuesses: Seq[String], numToReturn: Int): Seq[String] = {
    val words = remainingWords.map{l: XordlePhrase => l.phrase}.toSeq
    val charFreqMap = createCharFreqMap(words)
    val charIdxFreqMap = createCharIndexFreqMap(words)

    remainingWords.map { word: XordlePhrase =>
      (word, scoreWord(word.phrase, charFreqMap, charIdxFreqMap))
    }.toSeq.sortBy(-_._2).map(_._1.phrase).toVector.take(numToReturn)
  }

  def scoreWord(word: String, charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int]): Int = {
    val rc = repeatingChars(word)
    word.zipWithIndex.map{ case (ch: Char, idx: Int) =>
      if (rc.contains(ch)) 0
      else charFreqMap.getOrElse(ch, 0) + 5 * charIdxFreqMap.getOrElse((ch, idx), 0)
    }.sum
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


