package com.sso.apg.policy.util;

import java.util.Random;

import org.apache.log4j.Logger;

public class SMCommonUtil {

	private static final Logger logger = Logger.getLogger(SMCommonUtil.class);
	public static String generateOID(){
		String OID = new String();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		Random random100 = new Random();
		int number = random100.nextInt(100);
		Random random26 = new Random();
		for (int i = 0 ; i<5 ; i++){
			int alphaPos = random26.nextInt(26);
			OID = OID + String.valueOf(alphabet.charAt(alphaPos));
		}
		OID = OID + number;
		logger.debug("Generated OID : " + OID);
		return OID;
	}
}
