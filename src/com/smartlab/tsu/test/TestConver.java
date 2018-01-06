package com.smartlab.tsu.test;

import com.smartlab.tsu.util.HexConverUtil;

public class TestConver {

	public static void main(String[] args) {
		String s="7F";
		if(HexConverUtil.IsHex(s)){
			System.out.println("->"+s);
		}

	}


}
