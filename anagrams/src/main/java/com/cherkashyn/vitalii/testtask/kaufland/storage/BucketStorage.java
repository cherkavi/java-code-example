package com.cherkashyn.vitalii.testtask.kaufland.storage;

import java.util.Iterator;

public interface BucketStorage<T> {
	T getStorage(Integer bucket);
	T createStorage(Integer bucket);
	Iterator<Integer> getBuckets();
}
