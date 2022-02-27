package com.skidis.wordle
package nerdle

object NerdleOperator extends Enumeration {
  type NerdleOperator = Char
  val Add = '+'
  val Subtract = '-'
  val Multiply = '*'
  val Divide = '/'
  val operators = Seq(Add, Subtract, Multiply, Divide)
}
