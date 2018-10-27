/**************************************************************************************************
  Filename:       Conversion.java
  Revised:        $Date: 2013-08-30 12:02:37 +0200 (fr, 30 aug 2013) $
  Revision:       $Revision: 27470 $

  Copyright 2013 Texas Instruments Incorporated. All rights reserved.
 
  IMPORTANT: Your use of this Software is limited to those specific rights
  granted under the terms of a software license agreement between the user
  who downloaded the software, his/her employer (which must be your employer)
  and Texas Instruments Incorporated (the "License").  You may not use this
  Software unless you agree to abide by the terms of the License. 
  The License limits your use, and you acknowledge, that the Software may not be 
  modified, copied or distributed unless used solely and exclusively in conjunction 
  with a Texas Instruments Bluetooth device. Other than for the foregoing purpose, 
  you may not use, reproduce, copy, prepare derivative works of, modify, distribute, 
  perform, display or sell this Software and/or its documentation for any purpose.
 
  YOU FURTHER ACKNOWLEDGE AND AGREE THAT THE SOFTWARE AND DOCUMENTATION ARE
  PROVIDED �AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED,
  INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, TITLE,
  NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT SHALL
  TEXAS INSTRUMENTS OR ITS LICENSORS BE LIABLE OR OBLIGATED UNDER CONTRACT,
  NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION, BREACH OF WARRANTY, OR OTHER
  LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT DAMAGES OR EXPENSES
  INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL, INDIRECT, PUNITIVE
  OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF PROCUREMENT
  OF SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY CLAIMS BY THIRD PARTIES
  (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), OR OTHER SIMILAR COSTS.
 
  Should you have any questions regarding your right to use this Software,
  contact Texas Instruments Incorporated at www.TI.com

 **************************************************************************************************/
package com.a1anwang.utils;

/* This class encapsulates utility functions */
public class Conversion {

	public static byte loUint16(short v) {
		return (byte) (v & 0xFF);
	}

	public static byte hiUint16(short v) {
		return (byte) (v >> 8);
	}

	public static short buildUint16(byte hi, byte lo) {
		return (short) ((hi << 8) + (lo & 0xff));
	}

	public static boolean isAsciiPrintable(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiPrintable(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		if(hexString.length()%2!=0){
			hexString="0"+hexString;
		}

		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private final static byte[] hex = "0123456789ABCDEF".getBytes();

	// 从字节数组到十六进制字符串转�?
	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	public static int byteStrToInt(String valueStr) {
		valueStr = valueStr.toUpperCase();
		if (valueStr.length() % 2 != 0) {
			valueStr = "0" + valueStr;
		}

		int returnValue = 0;

		int length = valueStr.length();

		for (int i = 0; i < length; i++) {

			int value = charToByte(valueStr.charAt(i));

			returnValue += Math.pow(16, length - i - 1) * value;
		}
		return returnValue;
	}

	public static int bytesToInt(byte[] values) {

		String valueStr = Bytes2HexString(values);
		if (valueStr.length() % 2 != 0) {
			valueStr = "0" + valueStr;
		}

		int returnValue = 0;

		int length = valueStr.length();

		for (int i = 0; i < length; i++) {

			int value = charToByte(valueStr.charAt(i));

			returnValue += Math.pow(16, length - i - 1) * value;
		}
		return returnValue;
	}
	/** 
	 * Byte转Bit 
	 */  
	public static String byteToBit(byte b) {  
	    return "" +(byte)((b >> 7) & 0x1) +   
	    (byte)((b >> 6) & 0x1) +   
	    (byte)((b >> 5) & 0x1) +   
	    (byte)((b >> 4) & 0x1) +   
	    (byte)((b >> 3) & 0x1) +   
	    (byte)((b >> 2) & 0x1) +   
	    (byte)((b >> 1) & 0x1) +   
	    (byte)((b >> 0) & 0x1);  
	}  
	  
	/** 
	 * Bit转Byte 
	 */  
	public static byte BitToByte(String byteStr) {  
	    int re, len;  
	    if (null == byteStr) {  
	        return 0;  
	    }  
	    len = byteStr.length();  
	    if (len != 4 && len != 8) {  
	        return 0;  
	    }  
	    if (len == 8) {// 8 bit处理  
	        if (byteStr.charAt(0) == '0') {// 正数  
	            re = Integer.parseInt(byteStr, 2);  
	        } else {// 负数  
	            re = Integer.parseInt(byteStr, 2) - 256;  
	        }  
	    } else {//4 bit处理  
	        re = Integer.parseInt(byteStr, 2);  
	    }  
	    return (byte) re;  
	} 
	
	/**
	 * 
	 * @param src
	 * @param start
	 * @param length
	 * @return   
	 */
	public static byte[] subByteArray(byte[] src,int start,int length){
		 
		
		byte[] result=new byte[length];
		
		System.arraycopy(src, start, result, 0, length);
		
		
		return result;
		
	}
	
}