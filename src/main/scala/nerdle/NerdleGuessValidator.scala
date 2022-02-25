package com.skidis.wordle
package nerdle

import input.BasicLenAndCharValidator

trait NerdleGuessValidator extends BasicLenAndCharValidator with GuessValidator with EquationParser {
  override def invalidCharMsg(validChars: Seq[Char]) = s"Input may only contain letters"

  override def validateGuess(input: String): Option[String] = {
    validateGuess(input, inputLength, validGuessChars, validResultChars) match {
      case Some(error) => Some(error)
      case None => parseEquation(input) match {
        case Left(error) => Some(error)
        case _ => None
      }
    }
  }
}

object NerdleGuessValidator extends NerdleGuessValidator
