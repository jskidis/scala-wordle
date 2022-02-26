package com.skidis.wordle

trait WordPatternMatcher {
  // Proactively not doing case insensitive here for performance reasons, may revisit
  def doesWordMatch(word: String, wordPattern: List[(Char, HintBlock)]): Boolean = {

    // If a the word pattern contains a Blank letter doesn't mean that the word is excluded if it contains that letter
    // Since the word pattern could still contain a Green or Yellow (or both or multiple) for that letter
    // As long as the word doesn't have more of the letter than the number of Green or Yellow of that letter, it's good
    def letterSurpassMaxCount(letter: Char): Boolean = {
      val letterCount = word.count { l => l == letter }
      val maxCount = wordPattern.count {
        case (_, _: MissHint) => false
        case (l, _: HintBlock) => l == letter
      }
      letterCount > maxCount
    }

    word.length == wordPattern.length && !wordPattern.zipWithIndex.exists {
      // if any letter pattern violates rules, return true which automatically short circuit .exists
      // if each letter returns false then .exists returns false (meaning no violations)
      // !exists means word didn't violate any rules so it is valid
      case ((letter, _: InPosHint), index) => word(index) != letter
      case ((letter, _: InWordHint), index) => !word.contains(letter) || word(index) == letter
      case ((letter, _: MissHint), _) => letterSurpassMaxCount(letter)
    }
  }
}

object WordPatternMatcher extends WordPatternMatcher
