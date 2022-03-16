package com.skidis.wordle
package strategy

case class WordScore(word: XordlePhrase, score: Double)

trait WordScoring {
  def scoreAndSortWords(scoreWordFunction: (XordlePhrase, WordSet) => Double)(remainingWords: WordSet)
  : Vector[String] = {
    val wordScores = remainingWords.map { potentialAnswer: XordlePhrase =>
      WordScore(potentialAnswer, scoreWordFunction(potentialAnswer, remainingWords))
    }

    // Next Guess is based on the words with most unique clusters, with ties resolved based on type
    val sortedWordScores = wordScores.toVector.sortBy(-_.score)
    sortedWordScores.map(_.word.phrase)
  }
}
