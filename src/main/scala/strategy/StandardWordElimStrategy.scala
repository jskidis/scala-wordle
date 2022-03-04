package com.skidis.wordle
package strategy

trait StandardWordElimStrategy extends SolveStrategy with WordPatternMatcher {
  override def reduceWordSet(wordSet: WordSet, currentGuess: String, wordHints: WordHints): WordSet = {
    // Create list of tuple with each letter of the current word and the hint for that letter
    val wordPattern: Seq[(Char, HintBlock)] = currentGuess.toSeq zip wordHints

    // Eliminate words from set that don't fit word hints for current answer
    wordSet.filter { w: XordlePhrase => doesWordMatch(w.phrase, wordPattern) && w.phrase != currentGuess }
  }
}
