package com.skidis.wordle

trait WordHintsGenerator extends HintProps {

  def generateWordHints(answer: XordlePhrase, word: XordlePhrase): WordHints = {
    generateWordWordHints(answer.phrase, word.phrase)
  }

  def generateWordWordHints(answer: String, word: String): WordHints = {
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
  }
}
