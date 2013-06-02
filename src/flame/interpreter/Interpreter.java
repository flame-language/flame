
package flame.interpreter;

import java.util.Enumeration;
import java.util.LinkedList;

import flame.core.FlameScope;
import flame.core.FlameValue;
import flame.core.FlameVariable;
import flame.reflect.FlameReflection;
import flame.syntaxtree.AdditiveExpression;
import flame.syntaxtree.IfExpression;
import flame.syntaxtree.JavaStaticMethods;
import flame.syntaxtree.MathExpression;
import flame.syntaxtree.MultiplicativeExpression;
import flame.syntaxtree.NodeChoice;
import flame.syntaxtree.NodeSequence;
import flame.syntaxtree.NodeToken;
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



public class Interpreter implements Interpret {

	/**
	 * here is start point for interpreting Flame
	 * @throws Exception 
	 */
	public Object visit(Start node) throws Exception {
		
		Enumeration importedPackagesEnum = (Enumeration) node.f0.elements();
		while( importedPackagesEnum.hasMoreElements() )
		{
			// adding required packages
			NodeSequence ns = (NodeSequence) importedPackagesEnum.nextElement();
			FlameReflection.pushPackage( this.visit( (Require) ns.elementAt( 0 ) , null).toString() );
		}

		 if( node.f1.size() > 0 )
		 {
			//~ creating of parent scope
			 FlameScope parent = new FlameScope( null );
			 Enumeration statement = (Enumeration) node.f1.elements();
			 while( statement.hasMoreElements() )
			 {
				 this.visit( (StatementExpression)statement.nextElement() , parent); 
			 }
		 }
		
		 return null;
	}

	/**
	 * this method allow retrieving and transforming required packages
	 * to Java format 
	 */
	public Object visit(NodeChoice f0, FlameScope scope, Object... objects) {
		/*
		 * before begin: 
		 * 		notice to all reserved keywords has ignored !
		 * 		f0 : content a keyword we ignore it
		 */
		StringBuilder builder = new StringBuilder();
		Enumeration element = (Enumeration) ((NodeSequence) f0.f1).elements();
		while( element.hasMoreElements() )
		{
			builder.append( element.nextElement() ) ;
			if( element.hasMoreElements() )
			{
				builder.append( "." );
			}
		}
		return builder;
	}

	/**
	 * integers operation
	 */
	public Object visit(MathExpression node, FlameScope scope, Object... objects) throws Exception {
		return this.visit(node.f0, scope, objects);
	}

	/**
	 * additive operations
	 */
	public Object visit(AdditiveExpression node, FlameScope scope,
			Object... objects) throws Exception {
		
		FlameValue value = (FlameValue)this.visit(node.f0, scope, objects);
		
		Enumeration e = (Enumeration) node.f1.elements();
		while( e.hasMoreElements() )
		{
			NodeSequence ns = (NodeSequence) e.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt( 0 );
			
			FlameValue tmp = (FlameValue) this.visit( (MultiplicativeExpression) ns.elementAt(1) , scope, objects);
			if( nc.choice.toString().equals("+") )
			{
				tmp.setValue( value.getValue() + tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("-") )
			{
				tmp.setValue( value.getValue() - tmp.getValue() );
				return tmp;
			}
		}
		return value;
	}

	/**
	 * multiplicative operation
	 */
	public Object visit(MultiplicativeExpression node, FlameScope scope,
			Object... objects) throws Exception {
		
		FlameValue value = (FlameValue)this.visit(node.f0, scope, objects);
		
		Enumeration e = (Enumeration) node.f1.elements();
		while( e.hasMoreElements() )
		{
			NodeSequence ns = (NodeSequence) e.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt( 0 );
			FlameValue tmp = (FlameValue) this.visit( (UnaryExpression) ns.elementAt(1) , scope, objects);
			
			if( nc.choice.toString().equals("*") )
			{
				tmp.setValue( value.getValue() * tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("/") )
			{
				tmp.setValue( value.getValue() / tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("%") )
			{
				tmp.setValue( value.getValue() % tmp.getValue() );
				return tmp;
			}
		}
		return value;
	}

	/**
	 * getting values
	 */
	public Object visit(UnaryExpression node, FlameScope scope,
			Object... objects) throws Exception {
		/*
		 * We are allowed just operation in IN (integers) ;)
		 */
		if( node.f0.choice instanceof NodeToken )
		{
			FlameValue value = new FlameValue();
			value.setValue( Long.parseLong( node.f0.choice.toString() ) );
			value.setType( long.class ); // here ;)
			return value;
		}
		else if( node.f0.choice instanceof VariableName )
		{
			String var = this.visit( (VariableName) node.f0.choice, scope, objects)
						.toString();
			if( scope.existsChild( var ) )
			{
				return scope.child( var ).getVariableValue();
			}
			else
			{
				throw new Exception("Sorry, but the variable " + var + " not exists =)!" );
			}
		}
		else if( node.f0.choice instanceof NodeSequence )
		{
			NodeSequence ns = (NodeSequence) node.f0.choice ;
			return this.visit( (MathExpression) ns.elementAt(1) , scope, objects) ;
		}
		return null;
	}

	/**
	 * relational testing ...
	 */
	public Object visit(RelationalExprssion node, FlameScope scope,
			Object... objects) throws Exception {
		
		return this.visit(node.f0, scope, objects);
	}

	/**
	 * testing for equality "1 == 1"
	 */
	public Object visit(RelationalEqualityExpression node, FlameScope scope,
			Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (RelationalGreaterExpression) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals("==") )
				{
					obj = Long.parseLong( obj.toString() ) ==  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals("!=") )
				{
					obj = Long.parseLong( obj.toString() ) !=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}

	/**
	 * testing for greater value "1 > 0"
	 */
	public Object visit(RelationalGreaterExpression node, FlameScope scope,
			Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (RelationalLessExpression) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals(">") )
				{
					obj = Long.parseLong( obj.toString() ) >  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals(">=") )
				{
					obj = Long.parseLong( obj.toString() ) >=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}
	
	/**
	 * test for less value "0 < 1"
	 */
	public Object visit(RelationalLessExpression node, FlameScope scope,
			Object... objects) throws Exception {
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (UnaryRelational) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals("<") )
				{
					obj = Long.parseLong( obj.toString() ) <  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals("<=") )
				{
					obj = Long.parseLong( obj.toString() ) <=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}

	/**
	 * method for getting a value to be tested
	 */
	public Object visit(UnaryRelational node, FlameScope scope,
			Object... objects) throws Exception {
		
		return ((FlameValue)this.visit(node.f0, scope, objects)).getValue() ;
	}

	/**
	 * St4tic "if" condition
	 */
	public Object visit(IfExpression node, FlameScope scope, Object... objects) throws Exception {
		
		/*
		 * like variable declaration we ignore all keywords, for more information
		 * see interface Interpret.java or JTB grammar
		 */
		FlameScope ifScope = new FlameScope( scope );
		if( new Boolean(this.visit(node.f1, scope, objects).toString()) )
		{
			Enumeration e = (Enumeration) node.f3.elements();
			while( e.hasMoreElements() )
			{
				this.visit( (StatementExpression)e.nextElement() , ifScope, objects);
			}
		}
		return null;
	}

	/**
	 * St4tic "while" expression
	 */
	public Object visit(WhileExpression node, FlameScope scope,
			Object... objects) throws Exception {
		/*
		 * like variable declaration we ignore all keywords, for more information
		 * see interface Interpret.java or JTB grammar
		 */
		FlameScope whileScope = new FlameScope( scope );
		while( new Boolean(this.visit(node.f1, scope, objects).toString()) )
		{
			Enumeration e = (Enumeration) node.f3.elements();
			while( e.hasMoreElements() )
			{
				this.visit( (StatementExpression)e.nextElement() , whileScope, objects);
			}
		}
		return null;
	}

	/**
	 * variable declaration and assignment
	 * @throws Exception 
	 */
	public Object visit(VariableDeclaration node, FlameScope scope,
			Object... objects) throws Exception {
		/*
		 * we ignore "def", "=" and "." keywords
		 */
		
		FlameVariable var = new FlameVariable();
		var.setVariableName( this.visit( node.f1 , scope, objects).toString() ) ;
		var.setVariableValue( (FlameValue) this.visit(node.f3, scope, objects) );
		
		/*
		 * we add a variable to current scope for variable life cycle
		 * and visibility.
		 */
		scope.pushChild( var.getVariableName() , var );
		return null;
	}

	/**
	 * assigning a new value to variable
	 * @throws Exception 
	 */
	public Object visit(VariableAssign node, FlameScope scope, Object... objects) throws Exception {
		String name = this.visit(node.f0, scope, objects).toString() ;
		if( scope.existsChild( name ) )
		{
			FlameVariable var = (FlameVariable) scope.child( name ) ;
			var.setVariableValue( (FlameValue) this.visit(node.f2, scope, objects) );
		}
		return null;
	}

	/**
	 * getting a variable name
	 */
	public Object visit(VariableName node, FlameScope scope, Object... objects) {
		return node.f0.tokenImage;
	}

	/**
	 * method for executing a static Java methods
	 * @throws Exception 
	 */
	public Object visit(JavaStaticMethods node, FlameScope scope,
			Object... objects) throws Exception {
		
		/*
		 * Okay, firstly we need to test existence of class and fields or method
		 * after, we get a value for arguments, finally we invoke a static Java Method 
		 */
		
		//f0 is class name 
		String identifier = FlameReflection.fullIdentifier( node.f0.tokenImage ) ;
		if( identifier != null )
		{
			// making a class object
			Object currentObject = FlameReflection.makeObject ( identifier );
			if( currentObject != null ){
				Enumeration e = (Enumeration) node.f1.elements();
				//~ getting a last field object
				while( e.hasMoreElements() )
				{
					NodeSequence ns = (NodeSequence) e.nextElement(); 
					if( FlameReflection.existsField( currentObject , ns.elementAt( 1 ).toString()  ) )
					{
						currentObject = FlameReflection.getFieldObject( currentObject , ns.elementAt( 1 ).toString() );
					}
					else
					{
						LinkedList<FlameValue> params = new LinkedList<FlameValue>();
						params.add( (FlameValue) this.visit(node.f3, scope, objects) );
						Enumeration eVal = (Enumeration) node.f4.elements();
						while( eVal.hasMoreElements() )
						{
							NodeSequence nsVal = (NodeSequence) eVal.nextElement();
							params.add( (FlameValue) this.visit( (MathExpression) nsVal.elementAt(1) , scope, objects) );
						}
						
						//~ test and invoking
						if( FlameReflection.existsSubroutine( currentObject , ns.elementAt( 1 ).toString() , params.toArray( new FlameValue[]{} )) )
						{
							return FlameReflection.invokeStaticSubroutine( currentObject , ns.elementAt( 1 ).toString() , params.toArray( new FlameValue[]{} )) ;
						}
						break;
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * Statement is the core of interpreting Flame source code
	 * @throws Exception 
	 */
	public Object visit(StatementExpression node, FlameScope scope,
			Object... objects) throws Exception {

		
		if( node.f0.choice instanceof VariableDeclaration )
		{
			return this.visit( (VariableDeclaration) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof VariableAssign )
		{
			return this.visit( (VariableAssign) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof JavaStaticMethods )
		{
			return this.visit( (JavaStaticMethods) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof IfExpression )
		{
			return this.visit( (IfExpression) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof WhileExpression )
		{
			return this.visit( (WhileExpression) node.f0.choice, scope, objects);
		}
		return null;
	}

	@Override
	public Object visit(Require node, FlameScope scope, Object... objects) {
		// TODO Auto-generated method stub
		return null;
	}

}
