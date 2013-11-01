class Main {
	public static void main(String args[]) {
		Utility.greet("Zoo Keeper");
		System.out.println("Here, take care of this parrot for me.");
		Zoo.breedParrot("Charles");
	}
}

class Zoo {
	public static void breedParrot(String name) {
		Parrot babyParrot = new Parrot(name);
	}
}

class Parrot {
	public Parrot(String name) {
		Utility.greet(name);
	}
}

class Utility {
	public static void greet(String name) {
		String greeting = String.format("My name is %s!", name);
		System.out.println(greeting);
	}
}