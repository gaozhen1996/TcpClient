package com.smartlab.tsu.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.smartlab.tsu.util.ConvertFactory;
import com.smartlab.tsu.util.FileUtil;
import com.smartlab.tsu.util.StringUtil;

public class TestHexToString {

	// 包头
	private static byte[] packageHead = ConvertFactory.hexStringToBytes("7FFF");

	// 包尾
//	private static byte[] packageEnd = ConvertFactory.hexStringToBytes("008001");

	public static void main(String[] args) {

		TestHexToString t = new TestHexToString();

		StringBuffer sb = new StringBuffer();
		sb = FileUtil.readFileByChars(new File("F:/downloadFile/share/mes//test90.txt"));
		String sbString = sb.toString();
		sbString = StringUtil.replaceBlank(sbString);
		byte[] megByte = ConvertFactory.hexStringToBytes(sbString);
		System.out.println(megByte.length);
		
		 t.test1(megByte);
		
		

	}

	public void test1(byte[] message) {
		
		int i = 0;
		while (i < message.length) {
		
			if(isHead(message, i)!=-1){
				
			}
			i++;
		}

		// byte[] megByte = message.getBytes();
		//
		// List<String> strlist = new ArrayList<>();
		//
		// int point_head = 0;
		// int point = 0;
		//
		// try {
		// while (point < megByte.length) {
		// // 验证包头
		// if (new String(megByte, point,
		// packageHead.length()).equals(packageHead))
		// {
		// // 指向头部的最后一个字符
		// point = point + packageHead.length();
		// // 忽略帧编号(相当于数据部分的头部)
		// point_head = point = point + 4;
		// // 指到数据部分的最后一个字符
		// point = point + packageBodyLength;
		// // 忽略校验码
		// point = point + 2;
		//
		// // 验证包尾
		// if (new String(megByte, point,
		// packageEnd.length()).equals(packageEnd)) {
		// strlist.addAll(parsePackageBody(new String(megByte, point_head,
		// packageBodyLength)));
		// // 指向包尾的最后一个字符
		// point = point + packageEnd.length();
		// } else {
		// System.out.println("尾部");
		// point = point + 2;
		// }
		// } else {
		// System.out.println("P->" + point);
		// System.out.println("头部");
		// point = point + 2;
		// }
		// }
		// } catch (Exception e) {
		// System.out.println("-----------");
		//
		// }
		//
		// for (int i = 0; i < strlist.size(); i++) {
		// System.out.println(HexConverUtil.HexToInt(strlist.get(i)));
		// }
	}
	
	private int isHead(byte[] data,int index){
		int res=-1;
		int count=0;
		
		for(int i=0;i<packageHead.length;i++,index++){
			if (data[index] == packageHead[i]) {
				count++;
				if(count==packageHead.length){
					res=index++;
				}
			}else{
				res=-1;
				break;
			}
		}
		
		
		return res;
	}
	
	

	@SuppressWarnings("unused")
	private List<String> parsePackageBody(String packageBody) {
		List<String> strlist = new ArrayList<>();

		int length = packageBody.length();
		for (int i = 0; i < length; i = i + 4) {
			String temp = new String(new char[] { packageBody.charAt(i + 2), packageBody.charAt(i + 3),
					packageBody.charAt(i), packageBody.charAt(i + 1) });
			strlist.add(temp);
		}

		return strlist;
	}

}
