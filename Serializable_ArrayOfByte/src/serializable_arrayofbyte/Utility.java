/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serializable_arrayofbyte;

/**
 *
 * @author cherkashinv
 */
public class Utility {

	public static int unsignedInt(int a) {
		if (a < 0) {
			return a + 256;
		}
		return a;
	}

	public static int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

	public static byte stringCharToBCDByte(String data, int pos) {
		return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
	}
    
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };


	@SuppressWarnings("unused")
	private final static String nullString = "null";

	/** получить в виде HEX текста */
	public static String hexDump(byte[] data) {
		return hexDump(data, 0, data.length);
	}
	/** получить в виде HEX текста */
	public static String hexDump(byte[] data, int length) {
		return hexDump(data, 0, length);
	}
	/** получить в виде HEX текста */
	public static String hexDump(byte[] data, int offset, int length) {
		String result = "";
		String part = "";
		for (int i = 0; i < min(data.length, length); i++) {
			part = ""
					+ hexChars[(byte) (unsignedInt(data[offset + i]) / 16)]
					+ hexChars[(byte) (unsignedInt(data[offset + i]) % 16)];
			result = result + part;
		}
		return result;
	}

	final static public int tagLen = 1;

	public static int getTagIdentifier(byte[] data, int pos) {
		return data[pos];
	}

	public static String getString(byte[] data, int pos, int len){
		return new String(data, pos, len);
	}

	public static byte[] selectBytes(byte[] data, int pos, int len) {
		byte[] res = new byte[len];
		for (int i = 0; i < len; i++)
			res[i] = data[pos + i];
		return res;
	}
	// -- Вспомогательные методы
	// формирует строку hex определенной длины, добавлят слева нулями
	public static String intToHexStringF(int data, int len) {
		String Indata = Integer.toHexString(data).toUpperCase();
		String Outdata = "";
		for (int i = 0; i < (len - Indata.length()); i++) {
			Outdata = Outdata + "0";
		}
		Outdata = Outdata + Indata;
		return Outdata;
	};


}
