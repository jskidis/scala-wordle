package com.skidis.worlde

import scala.io.StdIn.readLine

object Wordle extends App {
  val result = GatherResult(readLine, Console.println, InputValidator.apply)
  println(s"Result: ${result.getOrElse("user exited before finishing")}")
}
