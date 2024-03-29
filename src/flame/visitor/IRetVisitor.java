/* Generated by JTB 1.4.7 */
package flame.visitor;

import flame.syntaxtree.*;

public interface IRetVisitor<R> {

  public R visit(final NodeChoice n);

  public R visit(final NodeList n);

  public R visit(final NodeListOptional n);

  public R visit(final NodeOptional n);

  public R visit(final NodeSequence n);

  public R visit(final NodeTCF n);

  public R visit(final NodeToken n);

  public R visit(final Start n);

  public R visit(final Require n);

  public R visit(final MathExpression n);

  public R visit(final AdditiveExpression n);

  public R visit(final MultiplicativeExpression n);

  public R visit(final UnaryExpression n);

  public R visit(final RelationalExprssion n);

  public R visit(final RelationalEqualityExpression n);

  public R visit(final RelationalGreaterExpression n);

  public R visit(final RelationalLessExpression n);

  public R visit(final UnaryRelational n);

  public R visit(final IfExpression n);

  public R visit(final WhileExpression n);

  public R visit(final VariableDeclaration n);

  public R visit(final VariableAssign n);

  public R visit(final VariableName n);

  public R visit(final JavaStaticMethods n);

  public R visit(final StatementExpression n);

}
