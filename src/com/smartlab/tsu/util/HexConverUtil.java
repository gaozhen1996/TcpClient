package com.smartlab.tsu.util;

public class HexConverUtil {
	
	
	
	//十六进制  
	public static boolean isHexNumber(String str){  
        boolean flag = false;  
        for(int i=0;i<str.length();i++){  
            char cc = str.charAt(i);  
            if(cc=='0'||cc=='1'||cc=='2'||cc=='3'||cc=='4'||cc=='5'||cc=='6'||cc=='7'||cc=='8'||cc=='9'||cc=='A'||cc=='B'||cc=='C'||  
                    cc=='D'||cc=='E'||cc=='F'||cc=='a'||cc=='b'||cc=='c'||cc=='c'||cc=='d'||cc=='e'||cc=='f'){  
                flag = true;  
            }  
        }  
        return flag;  
    } 
	
	
	/**
	 * 十六进制转int
	 * @param strHex
	 * @return
	 */
	public static int HexToInt(String strHex) {
		int nResult = 0;
		if (!IsHex(strHex))
			return -1;
		String str = strHex.toUpperCase();
		if (str.length() > 2) {
			if (str.charAt(0) == '0' && str.charAt(1) == 'X') {
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for (int i = 0; i < nLen; ++i) {
			char ch = str.charAt(nLen - i - 1);
			try {
				nResult += (GetHex(ch) * GetPower(16, i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nResult;
	}

	/**
	 *   计算16进制对应的数值
	 * @param ch 十六进制数
	 * @return
	 * @throws Exception
	 */ 
	private static int GetHex(char ch) throws Exception {
		if (ch >= '0' && ch <= '9')
			return (int) (ch - '0');
		if (ch >= 'a' && ch <= 'f')
			return (int) (ch - 'a' + 10);
		if (ch >= 'A' && ch <= 'F')
			return (int) (ch - 'A' + 10);
		throw new Exception("error param");
	}

	/**
	 *  判断是否是16进制数
	 * @param strHex
	 * @return
	 */
	public static boolean IsHex(String strHex) {
		int i = 0;
		if (strHex.length() > 2) {
			if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
				i = 2;
			}
		}
		for (; i < strHex.length(); ++i) {
			char ch = strHex.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))
				continue;
			return false;
		}
		return true;
	}
	
	/**
	 *  计算幂
	 * @param nValue
	 * @param nCount
	 * @return
	 * @throws Exception
	 */
	private static int GetPower(int nValue, int nCount) throws Exception {
		if (nCount < 0)
			throw new Exception("nCount can't small than 1!");
		if (nCount == 0)
			return 1;
		int nSum = 1;
		for (int i = 0; i < nCount; ++i) {
			nSum = nSum * nValue;
		}
		return nSum;
	}

	
}
