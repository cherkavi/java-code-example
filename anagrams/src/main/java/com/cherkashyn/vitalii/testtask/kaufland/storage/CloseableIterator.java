package com.cherkashyn.vitalii.testtask.kaufland.storage;

import java.io.Closeable;
import java.util.Iterator;

public interface CloseableIterator<T> extends Iterator<T>, Closeable{

}

