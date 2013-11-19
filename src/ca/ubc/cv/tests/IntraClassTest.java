package ca.ubc.cv.tests;

public class IntraClassTest {
	
	public static void A(String s) { B(0); }
	public static void B(int i) { main("CV!"); }
	
	// Visualize this method.
	public static void main(String s) {}
	
	public static void A1() { A("Duck"); }
	public static void A2() { A("Dog"); }
	
}