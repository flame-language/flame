
package flame.core;

public class FlameValue extends FlameObject{

	private Class type	= null;
	private long value = 0;
	
	/**
	 * Get a value class type
	 * @return Class
	 */
	public Class getType() {
		if ( type != null )
			return type;
		return long.class;
	}
	
	/**
	 * Set value class type
	 * @param type
	 */
	public void setType(Class type) {
		this.type = type;
	}
	
	/**
	 * Get value as long
	 * 
	 * @return long
	 */
	public long getValue() {
		return value;
	}
	
	/**
	 * Set value as long
	 * @param value
	 */
	public void setValue(long value) {
		this.value = value;
	}
}
