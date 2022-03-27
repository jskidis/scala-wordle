package com.skidis.wordle
package wordle.runner

import runners.XrdleSimulationRunner

trait WordleSimulationStandardRunner extends XrdleSimulationRunner with WordleStandardRunner
object WordleSimulationStandardRunner extends WordleSimulationStandardRunner with App {
  printResults(runSimulation())
}

trait WordleSimulationAnswerOnlyRunner extends XrdleSimulationRunner with WordleAnswerOnlyRunner
object WordleSimulationAnswerOnlyRunner extends WordleSimulationAnswerOnlyRunner with App {
  printResults(runSimulation())
}

trait WordleSimulationCharFreqRunner extends XrdleSimulationRunner with WordleCharFreqRunner
object WordleSimulationCharFreqRunner extends WordleSimulationCharFreqRunner with App {
  printResults(runSimulation())
}

trait WordleSimulationWordFreqRunner extends XrdleSimulationRunner with WordleWordFreqRunner
object WordleSimulationWordFreqRunner extends WordleSimulationWordFreqRunner with App{
  printResults(runSimulation())
}

trait WordleSimulationReverseRunner extends XrdleSimulationRunner with WordleReverseRunner
object WordleSimulationReverseRunner extends WordleSimulationReverseRunner with App {
  printResults(runSimulation())
}
