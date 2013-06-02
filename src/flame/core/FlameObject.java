
package flame.core;


public class FlameObject {

	private FlameObject parent  = null;
	
	/**
	 * void constructor
	 *
	 */
	public FlameObject()
	{
		
	}
	
	/**
	 * Constructor with parent object
	 * @param parent
	 */
	public FlameObject (FlameObject parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Get an object parent
	 * @return FlameObject
	 */
	public FlameObject getParent()
	{
		return parent;
	}
}
