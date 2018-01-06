package com.smartlab.tsu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author gaozhen
 * @see http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
 */
public class FileUtil {

	@SuppressWarnings({ "unchecked", "resource" })
	public static List<?> readList(String path) {
		FileInputStream freader;
		ArrayList<String> list = new ArrayList<String>();
		try {
			freader = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(freader);
			list = (ArrayList<String>) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void writeList(String path,List<?> list) {
		try {

			FileOutputStream outStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);

			objectOutputStream.writeObject(list);
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 以字符为单位读取文件内容，一次读多个字节
	 * 
	 * @param file
	 * @return StringBuffer
	 */
	public static StringBuffer readFileByChars(File file) {
		Reader reader = null;
		StringBuffer res = new StringBuffer();
		try {
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(file));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 屏蔽掉\r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					res.append(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							res.append(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return res;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 * @param content
	 */
	public static void createFile(File file, String content) {
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 追加
	 * @param fileName
	 * @param content
	 */
	public static void appendMethod(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
}
