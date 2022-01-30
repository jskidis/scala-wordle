package com.skidis.wordle

import scala.annotation.tailrec

object WordMatchesDetail {
  def apply(word: String, matchingDetail: MatchingDetail): Boolean = {
    word.length == matchingDetail.positionMatchedLetters.length &&
      positionLettersMatch(word.toUpperCase, matchingDetail.positionMatchedLetters) &&
      otherLettersMatch(word.toUpperCase, matchingDetail.otherMatchedLetters)
  }

  @tailrec
  private def positionLettersMatch(word: String, positionMatches: List[Option[Char]]): Boolean = {
    if (word.isEmpty) true
    else if (positionMatches.flatten.isEmpty) true // if there are no more positional matches, stop
    else positionMatches.head match {
      case Some(m) if m.toUpper != word.head => false
      case _ => positionLettersMatch(word.tail, positionMatches.tail)
    }
  }

  @tailrec
  private def otherLettersMatch(word: String, otherMatchedLetters: Set[Char]): Boolean = {
    if (otherMatchedLetters.isEmpty) true
    else if (!word.contains(otherMatchedLetters.head.toUpper)) false
    else otherLettersMatch(word, otherMatchedLetters.tail)
  }
}
