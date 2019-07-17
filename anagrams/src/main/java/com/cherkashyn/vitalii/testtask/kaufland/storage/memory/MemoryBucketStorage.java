package com.cherkashyn.vitalii.testtask.kaufland.storage.memory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cherkashyn.vitalii.testtask.kaufland.storage.BucketStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.StorageCreator;

public class MemoryBucketStorage<StorageType> implements BucketStorage<StorageType>{

	private final Map<Integer, StorageType> storage=new HashMap<>();
	private final StorageCreator<StorageType> creator;
	
	public MemoryBucketStorage(StorageCreator<StorageType> creator) {
		this.creator=creator;
	}
	
	@Override
	public StorageType getStorage(Integer bucket) {
		StorageType lineStorage=this.storage.get(bucket);
		if(lineStorage==null){
			lineStorage=this.createStorage(bucket);
			this.storage.put(bucket, lineStorage);
		}
		return lineStorage;
	}

	@Override
	public Iterator<Integer> getBuckets() {
		return this.storage.keySet().iterator();
	}

	@Override
	public StorageType createStorage(Integer bucket) {
		return this.creator.createNew(bucket);
	}

}
