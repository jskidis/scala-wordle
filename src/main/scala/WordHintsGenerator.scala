package com.skidis.wordle

import scala.collection.mutable

trait WordHintsGenerator extends HintProps {
  val maskingChar: Char = '#'

  def generateWordHints(answer: XordlePhrase, word: XordlePhrase): WordHints = {
    generateWordHints(answer.phrase, word.phrase)
  }

  def generateWordHints(answer: String, word: String): WordHints = {
    // The point of the first pass is two fold
    // 1. Identify hints the are in-position (same letter at a given position in both answer and word) or miss
    //    (letter in word is not in answer), leaving remainder temporarily as in-word hints to be addressed in 2nd pass
    // 2. Create a version of the answer where letters that have already been accounted for,
    //    because they were in-position hints, are masked with a special char such that during the second pass they
    //    aren't used when assessing whether a letter in the word should be a in-word hint
    val firstPass = word.zip(answer).map { case (wordLetter, answerLetter) =>
      if (wordLetter == answerLetter) (inPosHint, wordLetter, maskingChar)
      else if(!answer.contains(wordLetter)) (missHint, wordLetter, answerLetter)
      else (inWordHint, wordLetter, answerLetter)
    }

    // I know, I know, a mutable Array. Gasp!
    // But this version shaves about .5-.6 microseconds off of the foldLeft version
    // And when running this comparison tens of millions of times it makes a difference
    val maskedAnswer = mutable.ArraySeq(firstPass.map(_._3):_*)

    firstPass.map {
      case (_: InWordHint, letter: Char, _: Char) => // if letter was marked as in word in first pass ...
        // determine if the letter is (still) in the masked version of the answer
        val idx = maskedAnswer.indexOf(letter)
        if (idx == -1) missHint // if not, return a miss hint
        else {
          // return an in-word hint and mask the matching letter in the answer such that subsequent positions don't
          // "re-use" the letter to mark a hint as in-word
          maskedAnswer(idx) = maskingChar
          inWordHint
        }
      case (hint: HintBlock, _: Char, _: Char) => hint // If hint was marked in-position or miss in first pass, then already correct
    }
  }
}

/*
// The first pass creates a tuple with a hint, index, and letter
// The hint for in-position and miss are the correct final result
// If the letter is in-word the letter at that position is returned in the tuple
//    otherwise it is substituted with * so they can be ignore in the next pass
    val firstPass = word.zipWithIndex.map {
      case (letter, index) if letter == answer(index) => (inPosHint, index, '#')
      case (letter, index) if answer.contains(letter) => (inWordHint, index, letter)
      case (_, index) => (missHint, index, '#')
    }

  // Generate version of word that only includes letters that were initially marked as in-word
    val inWordOnlyWord = firstPass.map { case (_, _, l) => l }.mkString

// Generate a version of the answer that only include letters that were marked as in-word in the word but
// excluding any letter at the positions that were marked as in-position in the word
    val inWordOnlyAnswer = answer.zipWithIndex.map { case (letter, index) =>
      if (inWordOnlyWord.contains(letter) && firstPass(index)._1 != inPosHint) letter else '#'
    }.mkString

// loop through first pass results, if the first pass marked the letter as in-position or miss just return the hint
// if the hint is in-word and the number of times the letter is included (in the in-word only version) so far (based on index)
//    is greater then to the number times that letter occurs in the (in the in-word only answer) answer then flip the hint to miss
    firstPass map {
      case (hint: InPosHint, _, _) => hint // If hint is in-position then already correct
      case (hint: MissHint, _, _) => hint // If hint is miss then already correct
      case (_: HintBlock, index, letter) =>
        if (inWordOnlyWord.substring(0, index + 1).count(_ == letter) >
          inWordOnlyAnswer.count(_ == letter)) missHint else inWordHint
    }
*/
