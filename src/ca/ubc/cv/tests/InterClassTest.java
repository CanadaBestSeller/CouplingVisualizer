package ca.ubc.cv.tests;

class InterClassTest {

	public static class ClassA {
		public void A1() { A2(); }
		public void A2() { A3(); }
		public void A3() { ClassB.B3(); }
	}

	public static class ClassB {
		public void B1() { B2(); }
		public void B2() { B3(); }
		public static void B3() { ClassC.C3(); }
	}

	public static class ClassC {
		public void C1() { C2(); }
		public void C2() { C3(); }
		public static void C3() { ClassD.main(); }
	}

	public static class ClassD {
		public void D1() { D2(); }
		public void D2() { main(); }
		
		// Visualize this method.
		public static void main() {}
	}
}
