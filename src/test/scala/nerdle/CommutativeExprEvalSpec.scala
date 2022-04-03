package com.skidis.wordle
package nerdle

import nerdle.NerdleOperator.{Add, Divide, Multiply, Subtract}

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class CommutativeExprEvalSpec extends AnyFunSpec with Matchers {
  val equations: Seq[NerdleEquation] = NerdleGuessableGenerator.generate8CharEquations().toSeq

  val equivExprMap: Map[IntExpression, Seq[IntExpression]] = equations.map { eq: NerdleEquation =>
    (eq.expr, CommutativeExprEval.getEquivalentEqs(eq.expr))
  }.toMap

  val nonEmptyEquivsMap: Map[IntExpression, Seq[IntExpression]] = equivExprMap.filter {
    case (_: IntExpression, equivs: Seq[_]) => equivs.nonEmpty
  }

  describe("Commutative Expression Evaluator") {
    it("all commutative expressions should have the same value") {
      nonEmptyEquivsMap.map { case (expr: IntExpression, equivs: Seq[IntExpression]) =>
        equivs.foreach { equiv => equiv.value mustBe expr.value }
      }
    }

    it("one operator expressions") {
      // Addition and Multiplication generate a single equiv expression with operands reversed
      CommutativeExprEval.getEquivalentEqs(OneOperatorExpression(10, Add, 20)) mustBe
        Seq(OneOperatorExpression(20, Add, 10))

      CommutativeExprEval.getEquivalentEqs(OneOperatorExpression(5, Multiply, 20)) mustBe
        Seq(OneOperatorExpression(20, Multiply, 5))

      // Subtraction and Division don't generate any equiv expressions
      CommutativeExprEval.getEquivalentEqs(OneOperatorExpression(20, Subtract, 10)) mustBe Nil
      CommutativeExprEval.getEquivalentEqs(OneOperatorExpression(100, Divide, 20)) mustBe Nil
    }

    it("two operator expressions with the same operator") {
      // Addition and Multiplication generate a five equiv expressions with all combinations of operand order
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(1, Add, 2, Add, 7))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(2, Add, 1, Add, 7).toString,
          TwoOperatorExpression(7, Add, 1, Add, 2).toString,
          TwoOperatorExpression(7, Add, 2, Add, 1).toString,
          TwoOperatorExpression(1, Add, 7, Add, 2).toString,
          TwoOperatorExpression(2, Add, 7, Add, 1).toString
        )

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(2, Multiply, 3, Multiply, 4))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(3, Multiply, 2, Multiply, 4).toString,
          TwoOperatorExpression(4, Multiply, 2, Multiply, 3).toString,
          TwoOperatorExpression(4, Multiply, 3, Multiply, 2).toString,
          TwoOperatorExpression(2, Multiply, 4, Multiply, 3).toString,
          TwoOperatorExpression(3, Multiply, 4, Multiply, 2).toString
        )

      // Subtraction and Division don't generate any equiv expressions (this is how Nerdle handles it anyway)
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(10, Subtract, 7, Subtract, 2)) mustBe Nil
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(12, Divide, 4, Divide, 3)) mustBe Nil
    }

    it("two operator with mix of Addition and Multiplication") {
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(2, Multiply, 3, Add, 4))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(3, Multiply, 2, Add, 4).toString,
          TwoOperatorExpression(4, Add, 2, Multiply, 3).toString,
          TwoOperatorExpression(4, Add, 3, Multiply, 2).toString
        )

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(4, Add, 2, Multiply, 3))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(4, Add, 3, Multiply, 2).toString,
          TwoOperatorExpression(2, Multiply, 3, Add, 4).toString,
          TwoOperatorExpression(3, Multiply, 2, Add, 4).toString
        )
    }

    it("two operator with mix of Subtraction and Division") {
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(10, Divide, 2, Subtract, 3)) mustBe Nil
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(3, Subtract, 10, Divide, 5)) mustBe Nil
    }

    it("two operator with mix of Addition and either Subtraction or Division") {
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(2, Add, 9, Subtract, 1))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(9, Add, 2, Subtract, 1).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(9, Subtract, 1, Add, 2))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(2, Add, 9, Subtract, 1).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(10, Divide, 2, Add, 7))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(7, Add, 10, Divide, 2).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(7, Add, 10, Divide, 2))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(10, Divide, 2, Add, 7).toString)
    }

    it("two operator with mix of Multiplication and either Subtraction or Division") {
      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(3, Multiply, 4, Subtract, 2))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(4, Multiply, 3, Subtract, 2).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(10, Subtract, 2, Multiply, 3))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(10, Subtract, 3, Multiply, 2).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(4, Multiply, 5, Divide, 2))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(5, Multiply, 4, Divide, 2).toString)

      CommutativeExprEval.getEquivalentEqs(TwoOperatorExpression(12, Divide, 4, Multiply, 3))
        .map(_.toString) must contain theSameElementsAs
        Seq(TwoOperatorExpression(3, Multiply, 12, Divide, 4).toString)
    }
  }
}

