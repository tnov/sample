package jp.dip.fission.SampleRestApplication.tools;

public class TokenGeneratorMain {

	public static void main(String[] args) {
		for (int i = 1 ; i <= 100 ; i++)
		System.out.println(TokenGenerator.createToken());
	}

}
