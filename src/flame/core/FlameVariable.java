/**
 * The Code Project Open License (CPOL) 1.02
 * 		see : http://www.codeproject.com/info/cpol10.aspx
 * 
 * Author	: Adrabi Abderrahim
 * Mail		: adrabi[at]gmail[dot]com
 * Year		: 2009
 * Discrip	: this code a part of "Create Your Own Programming Language, just game ?" 
 * 			  and code using is from Alef++ project
 * 			  https://sourceforge.net/projects/alefpp/
 */

package flame.core;

/**
 * Class base for St4tic language variables
 * 
 * @author Adrabi Abderrahim
 *
 */
public class FlameVariable extends FlameObject {

	private String variableName = null;
	private FlameValue	variableValue = null;
	
	/**
	 * Get a variable name
	 * @return String
	 */
	public String getVariableName() {
		return variableName;
	}
	
	/**
	 * Set a name for variable
	 * @param variableName
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	/**
	 * Get a variable value
	 * @return St4ticValue
	 */
	public FlameValue getVariableValue() {
		return variableValue;
	}
	
	/**
	 * Set a value to variable
	 * @param variableValue
	 */
	public void setVariableValue(FlameValue variableValue) {
		this.variableValue = variableValue;
	}
	
}
