package core;

// Will later be used for debug tools
public class Debug{
	
	// Basic Log method
	/*
	 * All levels above the filter level will not be printed
	 * Level 1: Errors
	 * Level 2: Warnings
	 * Level 3: Program Status
	 */
	private static final int Log_Filter = 2;
	public static void Log(String message, int level) {
		if(level <= Log_Filter) System.out.println(message);
	}
}