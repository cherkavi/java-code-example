package com.cherkashyn.vitalii.testtask.kaufland.storage;

import com.cherkashyn.vitalii.testtask.kaufland.exception.StorageException;

public interface LineStorage {
	void addLine(String value) throws StorageException;
	CloseableIterator<String> getIterator();
}
