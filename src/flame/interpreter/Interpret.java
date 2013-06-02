
package flame.interpreter;

import flame.core.FlameScope;
import flame.syntaxtree.AdditiveExpression;
import flame.syntaxtree.IfExpression;
import flame.syntaxtree.JavaStaticMethods;
import flame.syntaxtree.MathExpression;
import flame.syntaxtree.MultiplicativeExpression;
import flame.syntaxtree.RelationalEqualityExpression;
import flame.syntaxtree.RelationalExprssion;
import flame.syntaxtree.RelationalGreaterExpression;
import flame.syntaxtree.RelationalLessExpression;
import flame.syntaxtree.Require;
import flame.syntaxtree.Start;
import flame.syntaxtree.StatementExpression;
import flame.syntaxtree.UnaryExpression;
import flame.syntaxtree.UnaryRelational;
import flame.syntaxtree.VariableAssign;
import flame.syntaxtree.VariableDeclaration;
import flame.syntaxtree.VariableName;
import flame.syntaxtree.WhileExpression;

public interface Interpret {
	   /**
	    * f0 -> ( Require() "." )+
	    * f1 -> ( StatementExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(Start node) throws Exception;

	   /**
	    * f0 -> "require"
	    * f1 -> ( <IDENTIFIER> )+
	    */
	   public Object visit(Require node, FlameScope scope, Object ... objects);

	   /**
	    * f0 -> AdditiveExpression()
	 * @throws Exception 
	    */
	   public Object visit(MathExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> MultiplicativeExpression()
	    * f1 -> ( ( "+" | "-" ) MultiplicativeExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(AdditiveExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> UnaryExpression()
	    * f1 -> ( ( "*" | "/" | "%" ) UnaryExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(MultiplicativeExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "(" MathExpression() ")"
	    *       | <INTEGER_LITERAL>
	    *       | VariableName()
	 * @throws Exception 
	    */
	   public Object visit(UnaryExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalEqualityExpression()
	 * @throws Exception 
	    */
	   public Object visit(RelationalExprssion node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalGreaterExpression()
	    * f1 -> ( ( "==" | "!=" ) RelationalGreaterExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalEqualityExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalLessExpression()
	    * f1 -> ( ( ">" | ">=" ) RelationalLessExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalGreaterExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> UnaryRelational()
	    * f1 -> ( ( "<" | "<=" ) UnaryRelational() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalLessExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> MathExpression()
	 * @throws Exception 
	    */
	   public Object visit(UnaryRelational node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "if"
	    * f1 -> RelationalExprssion()
	    * f2 -> "do"
	    * f3 -> ( StatementExpression() )*
	    * f4 -> "stop"
	 * @throws Exception 
	    */
	   public Object visit(IfExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "while"
	    * f1 -> RelationalExprssion()
	    * f2 -> "do"
	    * f3 -> ( StatementExpression() )*
	    * f4 -> "stop"
	 * @throws Exception 
	    */
	   public Object visit(WhileExpression node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "def"
	    * f1 -> VariableName()
	    * f2 -> "="
	    * f3 -> MathExpression()
	    * f4 -> "."
	 * @throws Exception 
	    */
	   public Object visit(VariableDeclaration node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> VariableName()
	    * f1 -> "="
	    * f2 -> MathExpression()
	    * f3 -> "."
	 * @throws Exception 
	    */
	   public Object visit(VariableAssign node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> <IDENTIFIER>
	    */
	   public Object visit(VariableName node, FlameScope scope, Object ... objects);

	   /**
	    * f0 -> <IDENTIFIER>
	    * f1 -> ( ":" <IDENTIFIER> )+
	    * f2 -> "("
	    * f3 -> MathExpression()
	    * f4 -> ( "," MathExpression() )*
	    * f5 -> ")"
	    * f6 -> "."
	 * @throws Exception 
	    */
	   public Object visit(JavaStaticMethods node, FlameScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> VariableDeclaration()
	    *       | VariableAssign()
	    *       | JavaStaticMethods()
	    *       | IfExpression()
	    *       | WhileExpression()
	 * @throws Exception 
	    */
	   public Object visit(StatementExpression node, FlameScope scope, Object ... objects) throws Exception;
}
