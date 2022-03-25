package com.skidis.wordle
package hintgen

trait WordPatternMatcher {
  def doesWordMatch(word: String, wordPattern: Seq[(Char, HintBlock)]): Boolean = {

    // If the word hints contain a miss letter doesn't mean that the word is excluded if it contains that letter
    // since the word pattern could still contain a in-position or in-word (or both or multiple) for that letter
    // As long as the word doesn't have more of the letter than the number of in-position or in-wordF of that letter, it's good
    def letterSurpassMaxCount(letter: Char): Boolean = {
      val letterCount = word.count { l => l == letter }
      val maxCount = wordPattern.count {
        case (_, _: MissHint) => false
        case (l, _: HintBlock) => l == letter
      }
      letterCount > maxCount
    }

    // If the word hints contains an in-word hint for a letter, it's not enough to check if the letter is contained
    // within the candidate word as that letter might also be elsewhere in the pattern in a in-pos or in-word position
    // So determine how many times the letter is in the word pattern with a in-pos or in-word hint and then
    // if the word doesn't contain that letter that many times then it should be excluded
    def letterNotEnoughTimes(letter: Char): Boolean = {
      val countInPattern = wordPattern.count {
        case (l, _: MissHint) => false
        case (l, _) => l == letter
      }
      val countInWord = word.count { l => l == letter }
      countInWord < countInPattern
    }

    word.length == wordPattern.length && !wordPattern.zipWithIndex.exists {
      // if any letter pattern violates rules, return true which automatically short circuit .exists
      // if each letter returns false then .exists returns false (meaning no violations)
      // !exists means word didn't violate any rules so it is valid
      case ((letter, _: InPosHint), index) => word(index) != letter
      case ((letter, _: InWordHint), index) => word(index) == letter || letterNotEnoughTimes(letter)
      case ((letter, _: MissHint), _) => letterSurpassMaxCount(letter)
    }
  }
}

object WordPatternMatcher extends WordPatternMatcher
