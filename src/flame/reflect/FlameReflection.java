
package flame.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import flame.core.FlameValue;



public class FlameReflection {
	
	private static LinkedList<String> packages = new LinkedList<String>();
	
	public static void pushPackage(String packageName )
	{
		packages.add( packageName );
	}
	
	/**
	 * Get a full class identifier 
	 * @param className (String : Class Name)
	 * @return
	 */
	public static String fullIdentifier(String className ){
		// Get a list of the used packages
		for( String pkg : packages ){
			try {
				
				Class.forName( pkg + "." + className);
				return pkg + "." + className;
				
			} catch (ClassNotFoundException e) {}
		}
		
		return null;
	}
	
	/**
	 * make object from string name
	 * @param className
	 * @return
	 */
	public static Class makeObject( String className )
	{
		try {
			return Class.forName( className ) ;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Check existence of  field 
	 * @param classInstance (Object : Class Object)
	 * @param fieldName (String : Filed Name)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean existsField(Object classInstance , String fieldName ){
		//~ Public Field
		try{
			Class clazz = classInstance instanceof Class ? (Class)classInstance : classInstance.getClass();
			clazz.getField( fieldName );
			
			return true;
			
		}catch (SecurityException e) {
		
		} catch (NoSuchFieldException e) {
			
		}
		return false;
	}
	
	/**
	 * Return a field object from specified class name
	 * @param classInstance (Object : Class Object)
	 * @param fieldName (String : Field Name)
	 * @return
	 */
	public static Object getFieldObject(Object classInstance , String fieldName ){
		//~ Public Field
		try{
			Field field = classInstance instanceof Class ? ((Class)classInstance).getField( fieldName ) : classInstance.getClass().getField( fieldName );
			return field.get(classInstance);
		}catch(IllegalAccessException iae){
			
		}catch(NoSuchFieldException nsfe){
			
		}
		
		return null;
	}
	
	/**
	 * Check existence of a subroutine with specified parameters
	 * @param classInstance (Object : Class Object)
	 * @param methodName (String : Subroutine Name)
	 * @param args (FlameValue[] : Types)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean existsSubroutine(Object classInstance, String methodName , FlameValue ... args){
		//~ Public Subroutine
		try{
			Class clazz = classInstance instanceof Class ? (Class)classInstance : classInstance.getClass();
			
			// Subtoutine with parameters "sub[param_1,params_2,...]"
			if( args != null ){
				LinkedList<Class> classes = new LinkedList<Class>();
				for( FlameValue value : args ){
					classes.add( value.getType() );
				}
				clazz.getMethod(methodName, classes.toArray(new Class[]{}));
			}
			// Subroutine without parameters "sub[]"
			else{
				clazz.getMethod(methodName, new Class[]{} );
			}
			
			return true;
		}catch(NoSuchMethodException nsfe){
			
		}
		
		return false;
	}
	
	/**
	 * For invoking a static subroutine and return value if is not void
	 * @param classInstance (Object : Class Object)
	 * @param methodName (String : Method Name)
	 * @param args (FlameValue[] : Method Arguments) 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeStaticSubroutine(Object classInstance, String methodName, FlameValue ... args){
		//~ Public Invokation
		try{
			Class clazz = classInstance instanceof Class ? (Class)classInstance : classInstance.getClass();
			
			// Invoke Subroutine with parameters
			if( args != null ){
				//~ Get Static Method
				LinkedList<Class> params = new LinkedList<Class>();
				for( FlameValue arg : args ){
					params.add( arg.getType() );
				}
				
				Method method = clazz.getMethod(methodName, params.toArray(new Class[]{}));
				
				//~ Invoke Static Method
				LinkedList<Object> values = new LinkedList<Object>();
				for( FlameValue arg : args ){
					values.add(arg.getValue());
				}
				
				return  method.invoke(classInstance, values.toArray(new Object[]{}));
			}
			// Invoke Subroutine without parameters
			else{
				Method method = clazz.getMethod(methodName, new Class[]{});
				return  method.invoke(classInstance, new Object[]{});
			}
			
		}catch (SecurityException se) {
			
		} catch (NoSuchMethodException nsme) {
			
		} catch (IllegalArgumentException iae) {
			
		} catch (IllegalAccessException iae) {
			
		} catch (InvocationTargetException ate) {
			
		}
		
		return null;
	}
}
