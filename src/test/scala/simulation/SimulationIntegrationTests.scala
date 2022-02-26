package com.skidis.wordle
package simulation

import org.scalatest.Ignore
import org.scalatest.funsuite.AnyFunSuite

@Ignore
class SimulationIntegrationTests extends AnyFunSuite {
  test("Wordle Simulation") {
    WordleSimulation.main(Array("answer-only", "3"))
    WordleSimulation.main(Array("reverse", "3"))
    WordleSimulation.main(Array("standard", "3"))
  }

  test("Nerdle Simulation") {
    NerdleSimulation.main(Array("standard", "3"))
  }
}
