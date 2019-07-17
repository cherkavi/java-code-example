/*******************************************************************************
 * Copyright 2009, Clark N. Hobbie
 * 
 * This file is part of the CLIPC library.
 * 
 * The CLIPC library is free software; you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * The CLIPC library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
 * License for more details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with the CLIP library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 *******************************************************************************/
package com.lts.ipc.sharedmemory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;

import com.lts.ipc.IPCException;
import com.lts.ipc.Messages;
import com.lts.ipc.Utils;

/**
 * <P>
 * A shared memory segment.
 * </P>
 * <H2>Description</H2>
 * <P>
 * This class wraps the {@link java.nio.MappedByteBuffer} class to make it a little easier
 * to use.
 * </P>
 * <P>
 * {@link #quickstart}
 * </P>
 * <P>
 * Instances of this class must correspond to a file on the host system that is read/write
 * to the running process. <A name="quickstart">
 * <H2>Quickstart</H2> </A>
 * <UL>
 * <LI>Create or connect to an existing segment via {@link #SharedMemory(String, int)}.</LI>
 * </LI>
 * <LI>Read from the segment via {@link #read(byte[], int, int, int)}.</LI>
 * <LI>Write to the segment via {@link #write(byte[], int, int, int)}.</LI>
 * <LI>You do not need to explicitly close your connection.</LI>
 * </UL>
 * 
 * @see java.nio.MappedByteBuffer
 * @author cnh
 */
public class SharedMemory
{
	protected File segmentFile;

	protected MappedByteBuffer byteBuffer;

	protected int size;

	protected boolean connected;

	protected FileChannel channel;

	private FileLock myLock;

	public MappedByteBuffer getByteBuffer()
	{
		return byteBuffer;
	}

	public void setByteBuffer(MappedByteBuffer byteBuffer)
	{
		this.byteBuffer = byteBuffer;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public File getSegmentFile()
	{
		return segmentFile;
	}

	public int getSize()
	{
		return size;
	}

	public SharedMemory()
	{
	}

	public SharedMemory(File file, int size) throws IPCException
	{
		connect(file, size);
	}

	public SharedMemory(String fileName, int size) throws IPCException
	{
		connect(fileName, size);
	}

	/**
	 * Connect this segment to the underlying shared memory segment.
	 * 
	 * @param name
	 *        The name of the file for the segment.
	 * @param size
	 *        The size of the segment.
	 */
	public void connect(File file, int size) throws IPCException
	{
		if (null != this.byteBuffer)
			throw new IPCException(Messages.ERROR_ALREADY_CONNECTED);

		Utils.checkFile(file);

		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile(file, "rw");
		}
		catch (FileNotFoundException e)
		{
			String msg = Messages.ERROR_OPEN_FAILED;
			throw new IPCException(msg, e);
		}

		channel = raf.getChannel();
		try
		{
			this.byteBuffer = channel.map(MapMode.READ_WRITE, 0, size);
		}
		catch (IOException e)
		{
			String msg = Messages.ERROR_MAP_EXCEPTION;
			throw new IPCException(msg, e);
		}

		this.segmentFile = file;
		this.size = size;
	}

	public void connect(String fileName, int size) throws IPCException
	{
		File file = new File(fileName);
		connect(file, size);
	}

	public void write(byte[] buf, int buffOffset, int bufLength, int segmentOffset)
			throws IPCException
	{
		try
		{
			if (null == this.byteBuffer)
				throw new IPCException(Messages.ERROR_NOT_CONNECTED);

			this.byteBuffer.position(segmentOffset);
			this.byteBuffer.put(buf, buffOffset, bufLength);
		}
		catch (java.nio.BufferOverflowException e)
		{
			System.out.println("segment size = " + this.size + ", segmentOffset = "
					+ segmentOffset + ", length = " + bufLength);
			throw e;
		}
	}

	public void write(byte value, int offset)
	{
		this.byteBuffer.position(offset);
		this.byteBuffer.put(value);
	}

	public void write(byte[] buf) throws IPCException
	{
		write(buf, 0, buf.length, 0);
	}

	public void write(int value, int offset)
	{
		byte b = (byte) value;
		write(b, offset);
	}

	public void write(byte[] buf, int segmentOffset) throws IPCException
	{
		write(buf, 0, buf.length, segmentOffset);
	}

	public void read(byte[] buf, int boffset, int length, int soffset)
			throws IPCException
	{
		if (null == this.byteBuffer)
			throw new IPCException(Messages.ERROR_NOT_CONNECTED);

		this.byteBuffer.position(soffset);
		this.byteBuffer.get(buf, boffset, length);
	}

	public void read(byte[] buf, int offset) throws IPCException
	{
		read(buf, 0, buf.length, offset);
	}

	public void read(byte[] buf) throws IPCException
	{
		read(buf, 0, buf.length, 0);
	}

	public void read(int offset, byte[] buf) throws IPCException
	{
		read(buf, 0, buf.length, offset);
	}

	public byte readByte(int offset) throws IPCException
	{
		return this.byteBuffer.get(offset);
	}

	public void lock() throws IOException
	{
		myLock = channel.lock();
	}

	public void unlock() throws IOException
	{
		myLock.release();
	}
}
