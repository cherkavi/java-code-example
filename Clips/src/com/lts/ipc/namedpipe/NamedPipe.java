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
package com.lts.ipc.namedpipe;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.lts.ipc.IPCException;
import com.lts.ipc.IPCPackage;
import com.lts.ipc.Utils;
import com.lts.ipc.namedpipe.NamedPipeImpl.PipeDirection;

/**
 * A one-way interprocess communications channel.
 * <UL>
 * <LI><A href="#note">Note</A></LI>
 * <LI><A href="#quickstart">Quickstart</A></LI>
 * <LI><A href="#description">Description</A></LI>
 * </UL>
 * <A name="note"><H2>NOTE</H2></A> Because of the way that Windows implements named
 * pipes, one process must call the {@link #create()} method before the named pipe can be
 * used. This is in addition to whether that process will be exclusively reading from or
 * writing to the pipe.
 * <P>
 * On Linux, a named pipe (FIFO) will persist after the process that created it
 * terminates, allowing subsequent clients to use it without taking on a client or role.
 * </P>
 * <P>
 * Unfortunately, using this information will introduce a platform dependency in the
 * system.
 * </P>
 * <A name="quickstart"><H2>Quickstart</H2></A> <H3>Creator/Writer Process</H3>
 * <P>
 * <CODE>
 * <PRE>
 * byte[] buffer;
 * String name;
 * // create an instance for buffer and fill it with data
 * // set name to a valid file name 
 * ... 
 * NamedPipe pipe = new NamedPipe(name);
 * pipe.create();
 * pipe.openWriter();
 * pipe.write(buffer);
 * </PRE>
 * </CODE>
 * <P>
 * <H3>Reader Process</H3>
 * <P>
 * <CODE>
 * <PRE>
 * byte[] buffer;
 * String name;
 * // create an instance for buffer
 * // set name to the same name as the writer is using
 * ...
 * NamedPipe pipe = new NamedPipe(name);
 * pipe.openReader();
 * int count = pipe.read(buffer);
 * // process the data received
 * ...
 * </PRE>
 * </CODE>
 * </P>
 * <A name="description"><H2>Description</H2></A> <A href="#note">Please see the note on
 * client/server roles before using this class</A>
 * <P>
 * This class provides a named pipe IPC primitive. These are sometimes referred to as
 * FIFOs or message queues.
 * </P>
 * <P>
 * In this context a named pipe provides a one-way, synchronous communications channel
 * between two processes. Note that this class only allows one-way communications even if
 * the underlying mechanism the operating system supports is two-way.
 * </P>
 * <P>
 * The class uses file naming <A href="../package-summary.html#file_naming"> as explained
 * in the package description.</A> In the case of this class, the file name will refer to
 * an operating system file. On Linux, this will be the name of the FIFO that is a file in
 * the file system that implements the pipe. On windows, this will be a file that contains
 * the actual name of the pipe.
 * </P>
 * <P>
 * Before using an instance of the class, the {@link #openReader()} or
 * {@link #openWriter()} method should be used to establish the role it will be taking
 * when communicating. Attempting to use an instance without properly initializing may
 * result in unpredictable behavior.
 * </P>
 * <P>
 * Once the role has been established the {@link #read(byte[])},
 * {@link #read(byte[], int, int)}, {@link #write(byte[])} and
 * {@link #write(byte[], int, int)} methods, as appropriate, can be used to send or
 * receive information.
 * </P>
 * <A name="virtual_file"><H2>Virtual File Name</H3></A> 
 * <P>
 * This class uses virtual and
 * actual files to implement the underlying primitive. On platforms such as Linux, the
 * virtual and actual file names are the same and both refer to a special file on the
 * system that represents the communications channel.
 * </P>
 * <P>
 * In the case of Windows, named pipes must use a special file name of the form: {@code
 * \\.\pipe\<name>}. Such file names cannot be used on Linux.
 * </P>
 * <P>
 * In order to provide a reasonably platform independent solution, the Windows
 * implementation of named pipes uses two file names for the pipe: a virtual name name and
 * an actual name.
 * </P>
 * <P>
 * The virtual name is basically any valid file name on the system. The file identified in
 * the virtual name corresponds to an actual file in the system, which contains the actual
 * name of the named pipe.
 * </P>
 * <P>
 * The actual name is then used to identify the named pipe to Windows.
 * </P>
 * 
 * @author cnh
 */
public class NamedPipe
{
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	private String myVirtualName;

	private PipeDirection myDirection;

	private NamedPipeImpl myImpl;

	protected NamedPipeImpl getImpl()
	{
		return myImpl;
	}

	protected void setImpl(NamedPipeImpl impl)
	{
		myImpl = impl;
	}

	public NamedPipe()
	{
		IPCPackage.ensureInitialized();
	}
	
	/**
	 * Create a named pipe with the specified virtual name.
	 * 
	 * @param virtualName
	 *        The file to be used for the named pipe. See the class description for
	 *        details about <A href="#virtual_file"> how and why the virtual file name is
	 *        used. </A>
	 */
	public NamedPipe(String virtualName)
	{
		initialize(virtualName);
	}

	protected void initialize(String virtualName)
	{
		initialize(virtualName, DEFAULT_BUFFER_SIZE);
	}

	private void initialize(String virtualName, int bufferSize)
	{
		NamedPipeImpl impl = new NamedPipeImpl();
		impl.setBufferSize(bufferSize);
		setVirtualName(virtualName);
		setImpl(impl);
	}

	public void create() throws IPCException
	{
		initializeCreate();
		NamedPipeResult result = getImpl().create();
		processCreateResult(result);
		processVirtualFile();
	}

	private void initializeCreate() throws IPCException
	{
		String actualName = determineActualName();

		NamedPipeImpl impl = new NamedPipeImpl();
		impl.setActualName(actualName);
		impl.setCreator(true);
		setImpl(impl);
	}

	private void processVirtualFile() throws IPCException
	{
		try
		{
			if (NamedPipeImpl.virtualNameContainsActualName())
			{
				String actualName = getImpl().getActualName();
				Utils.writeFile(getVirtualName(), actualName);
			}
		}
		catch (IOException e)
		{
			String msg = "Error trying to write out virtual file: " + getVirtualName();
			throw new IPCException(msg);
		}
	}

	private void processCreateResult(NamedPipeResult result) throws IPCException
	{
		if (NamedPipeResult.SUCCESS != result.resultCode)
		{
			String msg = null;

			switch (result.resultCode)
			{
				case NamedPipeResult.ERROR_ACCESS_DENIED:
					msg = "access to the named pipe was denied";
					break;

				case NamedPipeResult.ERROR_UNKNOWN:
				default:
					msg = "An unknown error was encountered while trying to create the named pipe";
					break;
			}

			throw new IPCException(msg);
		}
	}

	protected String determineActualName() throws IPCException
	{
		String temp;

		if (!NamedPipeImpl.virtualNameContainsActualName())
		{
			temp = getVirtualName();
		}
		else
		{
			temp = readActualName(getVirtualName());

			if (null == temp)
			{
				temp = createActualName();
			}
		}

		return temp;
	}

	protected String createActualName()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = sdf.format(System.currentTimeMillis());
		s = NamedPipeImpl.createPipeName(s);
		return s;
	}

	protected String readActualName(String virtualName) throws IPCException
	{
		try
		{
			String s = Utils.readFile(virtualName);
			return s;
		}
		catch (IOException e)
		{
			String msg = "An error was encountered while trying to read the actual "
					+ "name of a named pipe from its virtual name.";

			throw new IPCException(msg, e);
		}
	}

	/**
	 * Get the virutal name of the named pipe.
	 * <P>
	 * See the class description of virtual files for details about <A
	 * href="#virtual_file">how and why virtual file names are used</A>
	 * </P>
	 * 
	 * @return The virtual file name.
	 */
	public String getVirtualName()
	{
		return myVirtualName;
	}

	/**
	 * Set the virutal name of the named pipe.
	 * <P>
	 * The virtual file name of a named pipe is set at the time the pipe is created and
	 * should not be changed. Hence this method is protected.
	 * </P>
	 * <P>
	 * See the class description of virtual files for details about <A
	 * href="#virtual_file">how and why virtual file names are used</A>
	 * </P>
	 */
	protected void setVirtualName(String virtualName)
	{
		myVirtualName = virtualName;
	}

	/**
	 * Open the named pipe, taking on the role of reader or writer.
	 * <P>
	 * On Linux, users of named pipes need to either read or write to a named pipe, they
	 * should not do both. Therefore this method establishes how the client is going to
	 * use the pipe and then connects to it.
	 * </P>
	 * 
	 * @param direction
	 *        The role the client wants to take on: reader or writer.
	 * @throws IPCException
	 *         If a problem is encountered while trying to connect to the pipe.
	 */
	public void open(PipeDirection direction) throws IPCException
	{
		initializeOpen();
		NamedPipeResult result = getImpl().open(direction);
		processOpenResult(result);
	}

	private void initializeOpen() throws IPCException
	{
		String actualName = determineActualName();
		if (null == actualName)
		{
			String msg = "The named pipe, " + getVirtualName() + ", does not exist.";
			throw new IPCException(msg);
		}

		getImpl().setActualName(actualName);
	}

	private void processOpenResult(NamedPipeResult result) throws IPCException
	{
		if (NamedPipeResult.SUCCESS != result.resultCode)
		{
			switch (result.resultCode)
			{
				case NamedPipeResult.ERROR_INVALID_HANDLE:
				{
					String msg = "Attempt to listen to an unconnected named pipe.  "
							+ "Error code = " + result.errorCode;

					throw new IPCException(msg);
				}

				default:
				{
					String msg = "An unknown error was encountered while trying to listen "
							+ "for a client.  " + "Error code = " + result.errorCode;

					throw new IPCException(msg);
				}
			}
		}
	}

	/**
	 * Connect to the named pipe, taking on the role of writer.
	 * <P>
	 * This method is equivalent to calling {@link #open(PipeDirection)} with an argument
	 * of {@link PipeDirection#Writer}.
	 * </P>
	 * 
	 * @throws IPCException
	 *         If a problem is encountered while trying to connect to the pipe.
	 * @see #open(PipeDirection)
	 */
	public void openWriter() throws IPCException
	{
		setDirection(PipeDirection.Writer);
		open(PipeDirection.Writer);
	}

	/**
	 * Connect to the named pipe, taking on the role of reader.
	 * <P>
	 * This method is equivalent to calling {@link #open(PipeDirection)} with an argument
	 * of {@link PipeDirection#Reader}.
	 * </P>
	 * 
	 * @throws IPCException
	 *         If a problem is encountered while trying to connect to the pipe.
	 * @see #open(PipeDirection)
	 */
	public void openReader() throws IPCException
	{
		setDirection(PipeDirection.Reader);
		open(PipeDirection.Reader);
	}

	protected PipeDirection getDirection()
	{
		return myDirection;
	}

	protected void setDirection(PipeDirection direction)
	{
		myDirection = direction;
	}

	protected String getActualName()
	{
		return getImpl().getActualName();
	}

	/**
	 * Read from the named pipe, blocking until data becomes available.
	 * <P>
	 * This method is equivalent to calling {@linkplain #read(byte[], int, int)
	 * read(buffer, 0, buff.length)}. See that class for additional details.
	 * </P>
	 * 
	 * @param buffer
	 *        The buffer where the data read from the pipe should be placed.
	 * @return The number of bytes actually read.
	 * @throws IPCException
	 *         If a problem is encountered while reading from the named pipe.
	 * @see #read(byte[], int, int)
	 */
	public int read(byte[] buffer) throws IPCException
	{
		return read(buffer, 0, buffer.length);
	}

	/**
	 * Read some data from the named pipe, blocking if the data is not available.
	 * <P>
	 * This method will block if data is not available when it is called. The method may
	 * read less than the specified amount of data.
	 * </P>
	 * <P>
	 * It is an error on some platforms to read from a pipe that has been opened for
	 * writing.
	 * </P>
	 * 
	 * @param buffer
	 *        The buffer where the data read should be placed.
	 * @param offset
	 *        Where the data should start in the buffer.
	 * @param length
	 *        The size of the buffer, in bytes.
	 * @return The number of bytes read.
	 * @throws IPCException
	 *         If a problem is encountered while trying to read from the pipe.
	 */
	public int read(byte[] buffer, int offset, int length) throws IPCException
	{
		return getImpl().read(buffer, offset, length);
	}

	/**
	 * Write some data to the named pipe.
	 * <P>
	 * This method is equivalent to calling {@linkplain #write(byte[], int, int)
	 * write(buffer, 0, buffer.length)}. Please see that method for additional details.
	 * </P>
	 * 
	 * @param buffer
	 *        The buffer that contains the data.
	 * @return The number of bytes actually written.
	 * @throws IPCException
	 *         If a problem is encountered while writing the data.
	 */
	public int write(byte[] buffer) throws IPCException
	{
		return write(buffer, 0, buffer.length);
	}

	/**
	 * Write some data to the named pipe.
	 * <P>
	 * This method writes data to the named pipe and will block if the pipe is currently
	 * full.
	 * </P>
	 * <P>
	 * It is an error to write to a named pipe that the client opened as a reader.
	 * </P>
	 * <P>
	 * The method may perform a partial write, in which case only some of the data in 
	 * the buffer was actually written.  In that case, the return value will be 
	 * less than the length parameter.
	 * </P>
	 * 
	 * @param buffer
	 *        The buffer that contains the data to write.
	 * @param offset
	 *        Where in the buffer that the data starts.
	 * @param length
	 *        The number of bytes of data to write.
	 * @return The number of bytes of data that were actually written.
	 * @throws IPCException
	 *         If a problem is encountered while trying to write the data.
	 */
	public int write(byte[] buffer, int offset, int length) throws IPCException
	{
		return getImpl().write(buffer, offset, length);
	}
}
