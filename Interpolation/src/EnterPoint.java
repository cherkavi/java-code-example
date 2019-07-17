
public class EnterPoint {
	public static void main(String[] args) throws Exception{
		Lagrang lagrang=new Lagrang(new float[]{1f,2f,3f, 4f, 5f, 6f, 7f, 8f},
									new float[]{1f,4f,9f,16f,25f,36f,49f,64f});
		System.out.println(Math.round(lagrang.getValue(10f)));
	}
}
