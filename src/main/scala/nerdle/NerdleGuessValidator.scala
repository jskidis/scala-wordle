package com.skidis.wordle
package nerdle

import input.BasicLenAndCharValidator

trait NerdleGuessValidator extends BasicLenAndCharValidator
  with GuessValidator with EquationParser with HintProps with GuessProps {
  override def invalidCharMsg(validChars: Set[Char]) = s"Input may only contain letters"

  override def validateGuess(input: String): Option[String] = {
    validateGuess(input, guessWordLength, validGuessChars, validHintChars) match {
      case Some(error) => Some(error)
      case None => parseEquation(input) match {
        case Left(error) => Some(error)
        case _ => None
      }
    }
  }
}


