package compare_xls.strategy;

import java.util.List;

import compare_xls.source.IGridSource;

public class CompareSymmetricReversDecorator implements ICompare{
	private ACompareSymmetric compare;
	
	public CompareSymmetricReversDecorator(ACompareSymmetric compare){
		this.compare=compare;
	}

	public List<CompareResult> compare(IGridSource original,
			IGridSource destination) {
		return compare.compare(destination, original);
	}
}
