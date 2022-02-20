package com.skidis.wordle

import BlockColor.{Black, BlockColor, Green, Yellow}

import scala.collection.mutable

trait WordColorPatternGenerator {

  def generateWordColorPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    generateStringColorPattern(answer.string, word.string)
  }

  def generateStringColorPattern(answer: String, word: String): ColorPattern = {
    // The first pass creates a tuple with a color, index, and letter
    // The coloring for green and black are the correct final result
    //    (green = letter at correct posistion, black = letter not in answer)
    // If the letter is yellow the letter at that position is returned in the tuple
    //    otherwise it is substituted with * so they can be ignore in the next pass
    val firstPass = word.zipWithIndex.map {
      case (letter, index) if letter == answer(index) => (Green, index, '*')
      case (letter, index) if answer.contains(letter) => (Yellow, index, letter)
      case (_, index) => (Black, index, '*')
    }.toList

    // Generate version of word that only includes letters that were initially marked as yellow
    val yellowOnlyWord = firstPass.map { case (_, _, l) => l }.mkString

    // Generate a version of the answer that only include letters that were marked as yellow in the word but
    // excluding any letter at the positions that were marked as green in the word
    val yellowOnlyAnswer = answer.zipWithIndex.map { case (letter, index) =>
      if (yellowOnlyWord.contains(letter) && firstPass(index)._1 != Green) letter else '*'
    }.mkString

    // loop through first pass results, if the first pass marked the letter as green or black just return the color
    // if the color is yellow and the number of times the letter is included (in the yellow only version) so far (based on index)
    //    is greater then to the number times that letter occurs in the (in the yellow only answer) answer then flip the color to black
    firstPass map {
      case (color: BlockColor, _, _) if color != Yellow => color // If color is green or black then already correct
      case (Yellow, index, letter) =>
        if (yellowOnlyWord.substring(0, index + 1).count(_ == letter) >
          yellowOnlyAnswer.count(_ == letter)) Black else Yellow
    }
  }

  // This version of the function is used align it with the ColorPatternGenerator function signature
  //    re: (guess: String) => ColorPattern}
  // The curried function that provides the answer can used to generate color pattern for given guesses
  def generateColorPatternCurryable(answer: String)(guess: String): ColorPattern = {
    generateStringColorPattern(answer, guess)
  }
}

object WordColorPatternGenerator extends WordColorPatternGenerator

trait CachingWordColorPatternGenerator extends WordColorPatternGenerator {
  val patternCache: mutable.Map[(WordleWord, WordleWord), ColorPattern] =
    collection.mutable.Map[(WordleWord, WordleWord), ColorPattern]()

  override def generateWordColorPattern(answer: WordleWord, word: WordleWord): ColorPattern = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordColorPattern(answer, word))
  }
}
