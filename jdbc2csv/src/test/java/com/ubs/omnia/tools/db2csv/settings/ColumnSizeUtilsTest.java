package com.ubs.omnia.tools.db2csv.settings;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class ColumnSizeUtilsTest {

	@Test
	public void readString(){
		// given
		String value=" /* 3 2r 0m 3L 5 L*/  ";
		// when
		int[] size=ColumnSizeUtils.parseSize(value);
		Boolean[] alignment=ColumnSizeUtils.parseAlignment(value);
		
		// then
		
		Assert.assertTrue(Arrays.equals(new int []{3, 2, 0, 3, 5, 0}, size));
		Assert.assertTrue(Arrays.equals(new Boolean[]{true, true, null, false, true, false}, alignment));
	}
}
