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
package com.lts.ipc;

/**
 * <P>
 * The text of messages passed in exceptions thrown by com.lts.ipc
 * </P>
 * <H2>NOTE</H2>
 * <P>
 * This class is deprecated.  The intention is to replace it with subclasses of 
 * {@link IPCException} that define enums with the full set of exceptions.  For 
 * those users that want to internationalize strings or present more user-friendly
 * error messages, use that scheme instead of this.
 * </P>
 * 
 * <P>
 * The purpose of this class is to allow clients to define internationalized strings for
 * particular messages. To that end, each message has the form: <CODE>
 * <PRE>
 * com.lts.ipc.Message.[error|fail].text
 * </PRE>
 * </CODE>
 * </P>
 * 
 * @author cnh
 * @deprecated Use symbolic constants and enum tags defined by exception classes.
 */
public interface Messages
{
	public static final String prefix = Messages.class.getName();

	public static final String error = prefix + ".error.";

	public static final String fail = prefix + ".fail.";

	public static final String mqerror = prefix + ".messageQueue.error.";

	/**
	 * Indicates that the IPC call failed because of a timeout.
	 */
	public static final String FAIL_TIMEOUT = fail + "timeout";

	/**
	 * Create failed for an unanticipated reason.
	 * <UL>
	 * <LI>(Long) The platform specific error code.
	 * </UL>
	 */
	public static final String ERROR_CREATE_UNKNOWN = error + "createFailed";

	/**
	 * An error occurred on the native method level that is specific to the platform.
	 */
	public static final String ERROR_PLATFORM_SPECIFIC = error + "platformSpecific";

	/**
	 * A native method failed because the handle being used to identify the semaphore or
	 * whatever was not recognized as valid by the OS.
	 * <P>
	 * Someone may have "deleted it out from under" the client.
	 */
	public static final String ERROR_UNKNOWN_HANDLE = error + "unknownHandle";

	/**
	 * The method failed for an unanticipated reason. This is probably indicative of a
	 * bug.
	 */
	public static final String ERROR_UNKNOWN = error + "unknown";

	/**
	 * The java-side failed to create the file associated with an IPC resource. This means
	 * that {@link File#createNewFile()} returned false and that the file does not exist.
	 * <UL>
	 * <LI>(String) The name of the file that we tried to create
	 * </UL>
	 */
	public static final String ERROR_CREATE_FILE_FAILED = error + "createFileFailed";

	/**
	 * The java side failed to create a file associated with an IPC resource because of an
	 * exception. The cause property contains the offending error.
	 */
	public static final String ERROR_CREATE_EXCEPTION = error + "createException";

	/**
	 * An attempt was made to open a file, such as for use in a shared memory segment, and
	 * an exception was thrown.
	 * <UL>
	 * <LI>(String) The name of the file that the system tried to open
	 * </UL>
	 */
	public static final String ERROR_OPEN_FAILED = error + "openFailed";

	/**
	 * A call to
	 * {@link FileChannel#map(java.nio.channels.FileChannel.MapMode, long, long)} threw an
	 * exception.
	 * 
	 * @see FileChannel#map(java.nio.channels.FileChannel.MapMode, long, long)
	 */
	public static final String ERROR_MAP_EXCEPTION = error + "mapException";

	/**
	 * A call to connect a resource was made --- once connected, the classes in this
	 * package cannot be reconnected to another resource.
	 */
	public static final String ERROR_ALREADY_CONNECTED = error + "alreadyConnected";

	/**
	 * A request was made to perform some operation on an IPC class that was not connected
	 * to the underlying platform resource. For example, reading from an unconnected
	 * shared memory segment.
	 */
	public static final String ERROR_NOT_CONNECTED = error + "notConnected";

	/**
	 * The structure of the a shared memory segment that the system is using did not match
	 * the expected format.
	 */
	public static final String ERROR_INVALID_SHARED_MEMORY = error
			+ "invalidSharedMemory";

	/**
	 * An exception was caught while trying to read the resource.
	 */
	public static final String ERROR_READING = error + "reading";

	/**
	 * A client tried to send a message that was larger than the message size of the
	 * queue.
	 */
	public static final String ERROR_MESSAGE_TOO_LARGE = mqerror + "messageTooLarge";

	/**
	 * While waiting for an IPC resource to "free up" the thread was interrupted a la
	 * {@link InterruptedException}.
	 */
	public static final String ERROR_INTERRUPTED = error + "interrupted";

	/**
	 * The size needed for a message queue header became too large.
	 */
	public static final String ERROR_HEADER_TOO_LARGE = mqerror + "headerTooLarge";

	/**
	 * The system was asked to copy some information into a buffer that was too small.
	 */
	public static final String ERROR_BUFFER_TOO_SMALL = error + "bufferTooSmall";

	public static final String ERROR_TOO_MANY_INCREMENTS = error
			+ "semaphore incremented too many times";

	public static final String ERROR_PERMISSIONS = error + " insufficient permissions";
}
