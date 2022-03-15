package com.skidis.wordle
package waffle

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GridValidatorSpec extends AnyFunSpec with Matchers {
  describe("Grid Validator") {
    it("fails validation if there are not 6 words in the grid") {
      val grid = WaffleWordGrid(Seq(
        WaffleWord("ABCDE"), WaffleWord("VWXYZ")
      ))

      GridValidator.isGridValid(grid) mustBe false
    }

    it("fails validation if any of the words in the grid are not 5 characters") {
      val grid = WaffleWordGrid(Seq(
        WaffleWord("ABCDE"), WaffleWord("VWXYZ"), WaffleWord("LMNOPQRS"),
        WaffleWord("XYZ"), WaffleWord("ABCDE"), WaffleWord("VWXYZ"),
      ))

      GridValidator.isGridValid(grid) mustBe false
    }

    it("fails validation if the intersection characters aren't equal") {
      val grid1 = WaffleWordGrid(Seq(
        WaffleWord("AxBxC"), WaffleWord("DxExF"), WaffleWord("GxHxI"),
        WaffleWord("NxDxG"), WaffleWord("BxExH"), WaffleWord("CxFxI"),
      ))
      GridValidator.isGridValid(grid1) mustBe false

      val grid2 = WaffleWordGrid(Seq(
        WaffleWord("AxBxC"), WaffleWord("DxExF"), WaffleWord("GxHxI"),
        WaffleWord("AxDxG"), WaffleWord("BxExH"), WaffleWord("CxFxN"),
      ))
      GridValidator.isGridValid(grid2) mustBe false

      val grid3 = WaffleWordGrid(Seq(
        WaffleWord("AxBxC"), WaffleWord("DxExF"), WaffleWord("GxHxI"),
        WaffleWord("AxDxG"), WaffleWord("BxNxF"), WaffleWord("CxFxI"),
      ))
      GridValidator.isGridValid(grid3) mustBe false
    }

    it("passes validation when all the above conditions are met") {
      val grid = WaffleWordGrid(Seq(
        WaffleWord("AxBxC"), WaffleWord("DxExF"), WaffleWord("GxHxI"),
        WaffleWord("AxDxG"), WaffleWord("BxExH"), WaffleWord("CxFxI"),
      ))
      GridValidator.isGridValid(grid) mustBe true
    }
  }
}
