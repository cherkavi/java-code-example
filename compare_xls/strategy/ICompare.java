package compare_xls.strategy;

import java.util.List;

import compare_xls.source.IGridSource;

public interface ICompare {
	/** compare different source 
 	 * @param original - original value 
	 * @param destination - value for control values 
	 * @return
	 */
	public  List<CompareResult> compare(IGridSource original, IGridSource destination);
}
