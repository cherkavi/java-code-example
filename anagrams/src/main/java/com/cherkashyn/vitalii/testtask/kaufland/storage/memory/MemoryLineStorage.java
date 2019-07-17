package com.cherkashyn.vitalii.testtask.kaufland.storage.memory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.cherkashyn.vitalii.testtask.kaufland.storage.CloseableIterator;
import com.cherkashyn.vitalii.testtask.kaufland.storage.LineStorage;

public class MemoryLineStorage implements LineStorage{

	private List<String> storage=new LinkedList<String>();
	
	@Override
	public void addLine(String value) {
		this.storage.add(value);
	}

	@Override
	public CloseableIterator<String> getIterator() {
		return new ListCloseableIterator(this.storage.iterator());
	}


	class ListCloseableIterator implements CloseableIterator<String>{
		private final Iterator<String> iterator;

		ListCloseableIterator(Iterator<String> iterator){
			this.iterator=iterator;
		}
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public String next() {
			return iterator.next();
		}

		@Override
		public void close() throws IOException {
		}
	}
}
