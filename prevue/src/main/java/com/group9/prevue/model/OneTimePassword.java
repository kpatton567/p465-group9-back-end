package com.group9.prevue.model;

import java.util.Random;

public class OneTimePassword {
	
	public String pass(int len)
	{
		String otp = "";
		Random rand = new Random();
		String nums = "0123456789";
		for(int i = 0;i < len;i++)
		{
			otp += nums.charAt(rand.nextInt(nums.length()));
		}
		return otp;
	}
}
