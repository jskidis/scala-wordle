package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

object WordMatchesWordPattern {

  // Proactively not doing case insensitive here for performance reasons, may revisit
  def apply(word: String, wordPattern: List[(Char, BlockColor)]): Boolean = {
    word.length == wordPattern.length && !wordPattern.zipWithIndex.exists {
      // if any letter pattern violates rules, return true which automatically short circuit .exists
      // if each letter returns false then .exists returns false (meaning no violations)
      // !exists means word didn't violate any rules so it is valid
      case ((letter, Green), index) if word(index) != letter => true
      case ((letter, Yellow), index) if !word.contains(letter) || word(index) == letter => true
      case ((letter, Black), _) if word.contains(letter) => true
      case _ => false
    }
  }
}
