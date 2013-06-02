
package main;

import java.io.File;

import flame.Flame;

public class Main {
	public static void main(String [] $)
	{
	    try
	    {
	      if ($.length == 0) 
	      throw new Exception("Error, file doesn't exist");
	      else if (!new File( $[0] ).exists()) 
	      throw new Exception("Error, reading file : " + $ [0] + " !");
	      Flame parser = new Flame(new java.io.FileInputStream($ [0]));    
	      new flame.interpreter.Interpreter().visit( parser.Start() );
	    }
	    catch (Throwable e)
	    {
	    	System.out.println( "Error:\n" + e );
	    }
	}
}
