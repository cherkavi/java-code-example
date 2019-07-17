package com.cherkashyn.vitalii.testtask.kaufland.storage.file;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cherkashyn.vitalii.testtask.kaufland.storage.BucketStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.StorageCreator;

//TODO 
/**
 * user directories as bucket
 *
 * @param <StorageType>
 */
public class FileBucketStorage<StorageType> implements BucketStorage<StorageType>{

	private final Map<Integer, StorageType> storage=new HashMap<>();
	private final StorageCreator<StorageType> creator;
	
	public FileBucketStorage(StorageCreator<StorageType> creator) {
		this.creator=creator;
	}
	
	@Override
	public StorageType getStorage(Integer bucket) {
		// return one folder as directory
		StorageType lineStorage=this.storage.get(bucket);
		if(lineStorage==null){
			lineStorage=this.createStorage(bucket);
			this.storage.put(bucket, lineStorage);
		}
		return lineStorage;
	}

	@Override
	public Iterator<Integer> getBuckets() {
		// read directories for all sub-directories
		return this.storage.keySet().iterator();
	}

	@Override
	public StorageType createStorage(Integer bucket) {
		// create sub-directory
		return this.creator.createNew(bucket);
	}

}
