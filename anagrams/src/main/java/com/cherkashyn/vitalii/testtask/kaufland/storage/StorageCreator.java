package com.cherkashyn.vitalii.testtask.kaufland.storage;

public interface StorageCreator<T> {
	T createNew(Integer type);
}
