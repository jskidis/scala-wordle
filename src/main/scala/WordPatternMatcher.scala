package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

object WordPatternMatcher {
  // Proactively not doing case insensitive here for performance reasons, may revisit
  def doesWordMatch(word: String, wordPattern: List[(Char, BlockColor)]): Boolean = {

    // If a the word pattern contains a Black letter doesn't mean that the word is excluded if it contains that letter
    // Since the word pattern could still contain a Green or Yellow (or both or multiple) for that letter
    // As long as the word doesn't have more of the letter than the number of Green or Yellow of that letter, it's good
    def letterSurpassMaxCount(letter: Char): Boolean = {
      val letterCount = word.count { l => l == letter }
      val maxCount = wordPattern.count { case (l, c) => l == letter && c != Black }
      letterCount > maxCount
    }

    word.length == wordPattern.length && !wordPattern.zipWithIndex.exists {
      // if any letter pattern violates rules, return true which automatically short circuit .exists
      // if each letter returns false then .exists returns false (meaning no violations)
      // !exists means word didn't violate any rules so it is valid
      case ((letter, Green), index) => word(index) != letter
      case ((letter, Yellow), index) => !word.contains(letter) || word(index) == letter
      case ((letter, Black), _) => letterSurpassMaxCount(letter)
    }
  }
}
