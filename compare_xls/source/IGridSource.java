package compare_xls.source;

import java.util.Map;

import compare_xls.settings.ISettings;

public interface IGridSource {
	/** get maximum Rows */
	public int getMaxRowIndex();
	/** get String value from Sheet  */
	public String getStringValue(int rowIndex, int columnIndex);
	/** close source of data  */
	public void close();
	/** set settings 
	 * @param settings - Excel settings 
	 */
	public void setSettings( ISettings settings );
	/** Iterator#hasNext */
	public boolean hasNext();
	/** Iterator#next */
	public String[] next();
	/** get index of key column  */
	public int getKeyIndex();
	/** get records from source by key value  */
	public String[] getRecordsByKey(String keyValue);
	/** get compare map  */
	public Map<Integer, Integer> getCompareMap();
}
