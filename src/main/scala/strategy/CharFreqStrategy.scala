package com.skidis.wordle
package strategy

trait CharFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy {

  override def scoreWordFunction(remainingWords: WordSet): XordlePhrase => Double = {
    val words = remainingWords.map{l: XordlePhrase => l.phrase}.toSeq
    val charFreqMap = createCharFreqMap(words)
    val charIdxFreqMap = createCharIndexFreqMap(words)
    scoreWord(charFreqMap, charIdxFreqMap, remainingWords)
  }

  def scoreWord(charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int], remainingWords: WordSet)
    (potentialAnswer: XordlePhrase): Double = {

    val rc = repeatingChars(potentialAnswer.phrase)
    val letterScore = potentialAnswer.phrase.zipWithIndex.map{ case (ch: Char, idx: Int) =>
      if (rc.contains(ch)) 0
      else charFreqMap.getOrElse(ch, 0) + charIdxFreqMap.getOrElse((ch, idx), 0) * 5
    }.sum.toDouble
    letterScore / remainingWords.size
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


