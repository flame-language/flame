
package flame.core;

import java.util.Hashtable;


public class FlameScope extends FlameObject {

	private Hashtable <String,FlameVariable> childs = new Hashtable<String,FlameVariable>();

	/**
	 * Constructor for setting up parent
	 * @param parent
	 */
	public FlameScope( FlameObject parent )
	{
		super(parent);
	}
	
	/**
	 * return a childs hash
	 * @return
	 */
	public Hashtable <String,FlameVariable> getChilds()
	{
		return childs;
	}
	
	/**
	 * Add a child (variable) to scope for variable lifecycle and variable visibility
	 * 
	 * @param name
	 * @param child
	 * @return boolean
	 */
	public boolean pushChild(String name, FlameVariable child)
	{
		return this.childs.put( name , child) != null;
	}
	
	/**
	 * Return a child by name, fetching finding in current scope and parents 
	 * 
	 * @param name
	 * @return FlameVariable
	 */
	public FlameVariable child(String name)
	{
		return child ( name , this);
	}
	
	/**
	 * Find variable by name
	 * 
	 * @param name
	 * @param scope
	 * @return FlameVariable
	 */
	private FlameVariable child(String name, FlameScope scope)
	{
		if( scope.getChilds().containsKey( name ) )
		{
			return scope.getChilds().get( name );
		}
		else if( scope.getParent() != null && scope.getParent() instanceof FlameScope )
		{
			return child(name, (FlameScope)scope.getParent() );
		}
		return null;
	}
	
	/**
	 * Check existence of variables
	 * @param name
	 * @return boolean
	 */
	public boolean existsChild(String name){
		return existsChild(name, this);
	}
	
	/**
	 * Find variable by name
	 * 
	 * @param name
	 * @param scope
	 * @return boolean
	 */
	private boolean existsChild(String name, FlameScope scope)
	{
		if( scope.getChilds().containsKey( name ) )
		{
			return true;
		}
		else if( scope.getParent() != null && scope.getParent() instanceof FlameScope )
		{
			return existsChild(name, (FlameScope) scope.getParent());
		}
		return false;
	}
}
