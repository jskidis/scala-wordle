package com.skidis.wordle
package waffle

import input.InputToHintConversion
import wordle.{SimpleWordleWord, WordleGuessProps, WordleHintProps}

trait WaffleReader extends WordleHintProps with WordleGuessProps with InputToHintConversion {

  def readWaffle(words: Seq[Seq[Char]], hints: Seq[Seq[Char]], wordSet: WordSet): Either[String, WaffleDetailGrid] = {

    val upperWords = words.map(_.map(_.toUpper))
    val upperHints = hints.map(_.map(_.toUpper))

    if(upperWords.size != 5 && upperHints.size != 5) Left("words and hints must be in arrays of 5 strings")
    else if (!areCharsCorrect(upperWords, validGuessChars + ' ')) Left("words can only contain letters")
    else if (!areCharsCorrect(upperHints, validHintChars + ' ')) Left("hints can only contain matching hint characters")
    else if (!isMatrixRightSized(upperWords)) Left("word arrays must contain words with a length of 5")
    else if (!isMatrixRightSized(upperHints)) Left("hints arrays must contain words hints with a length of 5")
    else if (!arePadCharsInPlace(upperWords)) Left("word arrays must contain space char at empty spots on the waffle")
    else if (!arePadCharsInPlace(upperHints)) Left("hints arrays must contain space char at empty spots on the waffle\"")
    else Right(buildDetailGrid(upperWords, upperHints, wordSet))
  }

  def buildDetailGrid(words: Seq[Seq[Char]], hints: Seq[Seq[Char]], wordSet: WordSet): WaffleDetailGrid = {
    val flatWords = flattenWaffle(words)
    WaffleDetailGrid(flattenWaffle(words).zip(flattenWaffle(hints)).map {
      case (word: String, hints: String) =>
        WaffleWordDetail(SimpleWordleWord(word), convertInputToHints(hints), wordSet)
    })
  }

  def flattenWaffle(inputArrays: Seq[Seq[Char]]): Seq[String] = {
    Seq(
      inputArrays(0).mkString, inputArrays(2).mkString, inputArrays(4).mkString,
      inputArrays.map(_(0)).mkString, inputArrays.map(_(2)).mkString, inputArrays.map(_(4)).mkString
    )
  }

  def areCharsCorrect(inputArrays: Seq[Seq[Char]], validChars: Set[Char] ): Boolean = {
    inputArrays.forall(_.forall(validChars.contains))
  }

  def isMatrixRightSized(inputArrays: Seq[Seq[Char]]): Boolean = {
    inputArrays.forall(_.size == 5)
  }

  def arePadCharsInPlace(inputArrays: Seq[Seq[Char]]): Boolean = {
    inputArrays(1)(1) == ' ' && inputArrays(1)(3) == ' ' &&
      inputArrays(3)(1) == ' ' && inputArrays(3)(3) == ' '
  }
}

object WaffleReader extends WaffleReader