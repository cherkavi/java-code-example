
public class Test {

	public Object convert(Object object_for_convert) {
		byte[] return_value = null;
		try {
			String value=(String)(object_for_convert);
			return_value=hexToBytes(value.toCharArray());
		} catch (Exception ex) {
		}
		return return_value;
	}

	// -- перевод строки char в байт массив
	public byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] res = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127)
				value -= 256;
			res[i] = (byte) value;
		}
		return res;
	}

	public static void main(String[] args) {
		Test test = new Test();
		byte[] value=(byte[])test.convert("80F6001001");
		for(int counter=0;counter<value.length;counter++){
			System.out.println(counter+":"+value[counter]);
		}
	}

}
