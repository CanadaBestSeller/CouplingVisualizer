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
		public static void C3() { ClassG.main(); }
	}
	
	public static class ClassD {
		public void D1() { D2(); }
		public void D2() { D3(); }
		public static void D3() { ClassE.E3(); }
	}
	
	public static class ClassE {
		public void E1() { E2(); }
		public void E2() { E3(); }
		public static void E3() { ClassG.main(); }
	}

	public static class ClassF {
		public void F1() { F2(); }
		public void F2() { F3(); }
		public static void F3() { ClassG.main(); }
	}


	public static class ClassG {
		public void G1() { G2(); }
		public void G2() { main(); }
		
		// Visualize this method.
		public static void main() {}
	}
}
