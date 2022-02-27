package com.skidis.wordle
package nerdle

import input.BasicResultAndGuessValidator

trait NerdleInputValidator extends BasicResultAndGuessValidator with EquationParser  {

  override def validateGuess(input: String): Option[String] = {
    super.validateGuess(input) match {
      case Some(error) => Some(error)
      case None => parseEquation(input) match {
        case Left(error) => Some(error)
        case _ => None
      }
    }
  }
}


