package com.robert.authorization.server.util;

import java.security.SecureRandom;
import java.util.Base64;

public class CodeVerifier {
	private static final SecureRandom sr = new SecureRandom();

	public static String getCode() {
		byte[] code = new byte[32];
		sr.nextBytes(code);

		String codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
		System.out.println(codeVerifier);
		return codeVerifier;
	}
}
