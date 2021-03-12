package com.robert.authorization.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**
 * 
 * @author robert
 * CodeVerifier=sR0a5TBo1DmYMUpdHVjiHaC8pHCtyJGQLTj4nfq8vBo
	codeChallenge=40YFqVvcDivbT2g2XHsI4HMqJig1vXJ3jg_EAamWY5s
	executed at only once
 *
 */
public class CodeChallenge {

	public static void main(String[] args) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digested = md.digest(CodeVerifier.getCode().getBytes());
			String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digested);
			System.out.println(codeChallenge);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
/**
 * http://localhost:8080/oauth2/authorize?response_type=code&client_id=client1&scope=read&code_challenge=40YFqVvcDivbT2g2XHsI4HMqJig1vXJ3jg_EAamWY5s&code_challenge_method=S256
 * http://localhost:8080/authorized?code=R5WAP-igBTvO09dopmCIZuLk5YOgMpHtnjzm8hqJJOZdG9iMcQhXJg0qigPcRDabciGLcSqDtPpw4NIwQ1C2shscxwhfTwBScIbj5T-Usoe_6Ljqx9upKAvp2MZZWH3V
 */
}
