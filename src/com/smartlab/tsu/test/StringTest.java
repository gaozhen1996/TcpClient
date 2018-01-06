package com.smartlab.tsu.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.smartlab.tsu.util.HexConverUtil;
import com.smartlab.tsu.util.FileUtil;
import com.smartlab.tsu.util.StringUtil;

public class StringTest {

	// 数据包头部
	private String messageHead = "AA AA AA AA 00 01   " + "00 00 00 00 00 00 00 00 00 00   "
			+ "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ";

	// 数据包尾部
	private String messageEnd = "55 55 55 55";

	public static void main(String[] args) {
		
		StringBuffer sb = new StringBuffer();
		sb=FileUtil.readFileByChars(new File("F://a.txt"));
		System.out.println(sb.toString().length());
		
	}

	public List<DataInfoModel> test1(String message) {

		List<DataInfoModel> list = new ArrayList<>();
		
		message = StringUtil.replaceBlank(message);
		System.out.println(message);
		if (message.contains(messageHead)) {
			if (message.contains(messageEnd)) {
				StringBuffer messageBuffer = new StringBuffer(message);
				// 删除数据头部
				int start = messageBuffer.indexOf(messageHead);
				int end = messageHead.length();
				messageBuffer.delete(start, start + end);
				// 删除信息尾部
				start = messageBuffer.indexOf(messageEnd);
				end = messageBuffer.indexOf(messageEnd);
				messageBuffer.delete(start, start + end);
				String temp = messageBuffer.toString();

				temp = StringUtil.replaceBlank(temp);
				String[] arr = temp.split(" ");

				int i=1;
				for (String s : arr) {
					if (s.length() > 0) {
						list.add(new DataInfoModel(1, i, "2.02", HexConverUtil.HexToInt(s)));
						i++;
					}
				}
				
			} else {
				System.out.println("匹配尾部失败");
			}
		} else {
			System.out.println("匹配头部失败");
		}

		return list;
	}
}