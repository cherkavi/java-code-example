package com.cherkashyn.vitalii.testtask.kaufland;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import com.cherkashyn.vitalii.testtask.kaufland.exception.StorageException;
import com.cherkashyn.vitalii.testtask.kaufland.service.CompareStrategy;
import com.cherkashyn.vitalii.testtask.kaufland.service.NumberAmountStrategy;
import com.cherkashyn.vitalii.testtask.kaufland.storage.BucketStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.CloseableIterator;
import com.cherkashyn.vitalii.testtask.kaufland.storage.LineStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.StorageCreator;
import com.cherkashyn.vitalii.testtask.kaufland.storage.file.FileLineStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.memory.MemoryBucketStorage;
import com.cherkashyn.vitalii.testtask.kaufland.storage.memory.MemoryLineStorage;

public class App {

	
	public static void main(String[] args) throws IOException, RuntimeException, StorageException {
		File inputData = getFileFromArguments(args);

		// for huge amount of data need to use implementation {@link FileBucketStorage}
		BucketStorage<BucketStorage<LineStorage>> bucketStorage = new MemoryBucketStorage<>(
				new StorageCreator<BucketStorage<LineStorage>>() {
					@Override
					public BucketStorage<LineStorage> createNew(Integer type) {
						// for huge amount of data need to use implementation {@link FileBucketStorage}
						return new MemoryBucketStorage<>(new StorageCreator<LineStorage>() {
							@Override
							public LineStorage createNew(Integer type) {
								// for huge amount of data need to use {@link FileLineStorage}
								return new MemoryLineStorage();
							}
						});
					}
				});

		try (FileLineStorage fileLineStorage = new FileLineStorage(inputData)) {
			try (CloseableIterator<String> iterator = fileLineStorage.getIterator()) {
				while (iterator.hasNext()) {
					String valueForSave = iterator.next().trim();
					CompareStrategy strategy = new NumberAmountStrategy(valueForSave);
					// reduce by length of word
					BucketStorage<LineStorage> wordLengthStorage = bucketStorage.getStorage(valueForSave.length());
					// reduce by hash
					LineStorage hashStorage = wordLengthStorage.getStorage(strategy.getHash());
					// save value
					hashStorage.addLine(valueForSave);
				}
			}
		}

		// walk through all elements in storage
		Iterator<Integer> buckets = bucketStorage.getBuckets();
		while (buckets.hasNext()) {
			Integer nextBucketLength = buckets.next();
			BucketStorage<LineStorage> wordLengthStorage = bucketStorage.getStorage(nextBucketLength);

			Iterator<Integer> hashStorageBuckets = wordLengthStorage.getBuckets();
			while (hashStorageBuckets.hasNext()) {
				Integer nextHash = hashStorageBuckets.next();
				print(wordLengthStorage.getStorage(nextHash));
			}
		}

	}

	private final static String PRINT_POSTAMBULA = " ";

	private static void print(LineStorage storage) {
		CloseableIterator<String> iterator = null;
		try {
			StringBuilder returnValue = new StringBuilder();
			iterator = storage.getIterator();
			int counter = 0;
			while (iterator.hasNext()) {
				returnValue.append(iterator.next());
				returnValue.append(PRINT_POSTAMBULA);
				counter++;
			}
			if (counter > 1) {
				System.out.println(returnValue.toString());
			}
		} finally {
			IOUtils.closeQuietly(iterator);
		}

	}

	private static File getFileFromArguments(String[] args) {
		if (args.length == 0) {
			System.err.println("first argument should ");
			System.exit(1);
			return null;
		}
		File file = new File(args[0]);
		if (!file.exists() || !file.canRead()) {
			System.err.println("file should exists and be achievable " + args[0]);
			System.exit(1);
			return null;
		}
		return file;
	}
}
