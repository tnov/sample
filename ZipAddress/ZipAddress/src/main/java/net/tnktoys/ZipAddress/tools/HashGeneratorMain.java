package net.tnktoys.ZipAddress.tools;

public class HashGeneratorMain {

	public static void main(String[] args) {
		System.out.println(HashGenerator.createHash("test"));
		System.out.println(HashGenerator.createHash("tset"));
		System.out.println(HashGenerator.createHash("tset"));
		System.out.println(HashGenerator.createHash("test"));
	}

}
