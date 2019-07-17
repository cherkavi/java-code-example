package compare_xls.correctors;

public interface ICorrector {
	public int getIndex();
	public String correctValue(String value);
}
