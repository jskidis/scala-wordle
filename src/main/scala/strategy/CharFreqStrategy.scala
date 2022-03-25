package com.skidis.wordle
package strategy

trait CharFreqStrategy extends WordScoringStrategy with HardModeWordElimStrategy with CharFreqScorer {

  override def scoreWordFunction(remainingWords: WordSet): XrdleWord => Double = {
    val words = remainingWords.map{l: XrdleWord => l.text}.toSeq
    val charFreqMap = createCharFreqMap(words)
    val charIdxFreqMap = createCharIndexFreqMap(words)
    (word: XrdleWord) => {
      scoreWord(word, charFreqMap, charIdxFreqMap) / remainingWords.size.toDouble
    }
  }
}

object CharFreqStrategy extends CharFreqStrategy

trait CharFreqScorer {
  def scoreWord(potentialAnswer: XrdleWord, remainingWords: WordSet): Int = {
    val words = remainingWords.map{l: XrdleWord => l.text}.toSeq
    val charFreqMap = createCharFreqMap(words)
    val charIdxFreqMap = createCharIndexFreqMap(words)
    scoreWord(potentialAnswer, charFreqMap, charIdxFreqMap)
  }

  def scoreWord(potentialAnswer: XrdleWord,
    charFreqMap: Map[Char, Int], charIdxFreqMap: Map[(Char, Int), Int])
  : Int = {

    def repeatingChars(word: String): Seq[Char] = {
      word.groupBy(ch => ch).filter {
        case (_: Char, seq: String) => seq.length > 1
      }.keys.toSeq
    }

    val rc = repeatingChars(potentialAnswer.text)
    potentialAnswer.text.zipWithIndex.map{ case (ch: Char, idx: Int) =>
      if (rc.contains(ch)) 0
      else charFreqMap.getOrElse(ch, 0) + charIdxFreqMap.getOrElse((ch, idx), 0) * 5
    }.sum
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

object CharFreqScorer extends CharFreqScorer

