/* Generated by JTB 1.4.7 */
package flame.visitor;

import flame.syntaxtree.*;

public interface IVoidVisitor {

  public void visit(final NodeChoice n);

  public void visit(final NodeList n);

  public void visit(final NodeListOptional n);

  public void visit(final NodeOptional n);

  public void visit(final NodeSequence n);

  public void visit(final NodeTCF n);

  public void visit(final NodeToken n);

  public void visit(final Start n);

  public void visit(final Require n);

  public void visit(final MathExpression n);

  public void visit(final AdditiveExpression n);

  public void visit(final MultiplicativeExpression n);

  public void visit(final UnaryExpression n);

  public void visit(final RelationalExprssion n);

  public void visit(final RelationalEqualityExpression n);

  public void visit(final RelationalGreaterExpression n);

  public void visit(final RelationalLessExpression n);

  public void visit(final UnaryRelational n);

  public void visit(final IfExpression n);

  public void visit(final WhileExpression n);

  public void visit(final VariableDeclaration n);

  public void visit(final VariableAssign n);

  public void visit(final VariableName n);

  public void visit(final JavaStaticMethods n);

  public void visit(final StatementExpression n);

}
