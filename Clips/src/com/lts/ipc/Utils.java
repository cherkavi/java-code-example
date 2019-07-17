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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;

/**
 * An internal class that provides utility methods common to IPC classes.
 * 
 * @author cnh
 */
public class Utils
{
	/**
	 * Create a file if it does not already exist.
	 * <P>
	 * The IPC classes in this package all expect a file to exist that corresponds to the
	 * resource that they are manipulating. This method checks to see if the corresponding
	 * file does indeed exist on the underlying system. If it does not, then the method
	 * will try to create it.
	 * 
	 * @param name
	 *        The absolute path to the file.
	 * @throws IPCException
	 *         If the method tries to create the file but an exception is thrown. The
	 *         message will be set to {@link Messages#ERROR_CREATE_EXCEPTION} and the
	 *         cause will be set to the offending exception.
	 */
	public static void checkFile (File f) throws IPCException
	{
		IOException problem = null;
		if (!f.exists())
		{
			try
			{
				//
				// According to the javadoc, if java.lang.File.createNewFile returns 
				// normally, then the specified file now exists --- it was created by 
				// the method or someone else created it just before we made the call.
				// The only other possibility is an exception.
				//
				f.createNewFile();
			}
			catch (IOException e)
			{
				throw new IPCException(Messages.ERROR_CREATE_EXCEPTION, problem);
			}
		}
	}
	
	
	public static void checkFile (String name) throws IPCException
	{
		File file = new File(name);
		checkFile(file);
	}
	
	
	
	public static long toMilliseconds (long seconds, long nanos)
	{
		long result = seconds * 1000;
		result += nanos / 1000000;
		long temp = nanos % 1000000;
		if (temp > 0)
			result++;
		
		return result;
	}
	
	
	public static long toNanoseconds (long sec, long nanos)
	{
		long result = nanos;
		result = result + 1000000000 * sec;
		return result;
	}
	
	
	public static void closeNoExceptions(Reader reader)
	{
		if (null == reader)
			return;
		
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			; // ignore exceptions
		}
	}

	
	public static void closeNoExceptions(BufferedReader reader)
	{
		if (null == reader)
			return;
		
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			; // this method is supposed to ignore exceptions
		}
	}
	
	
	public static void closeNoExceptions(InputStream istream)
	{
		if (null == istream)
			return;
		
		try
		{
			istream.close();
		}
		catch (IOException e)
		{
			; // this method is supposed to ignore exceptions
		}
	}
	
	
	public static String readFile(String name) throws IOException
	{
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(name);
			StringWriter sw = new StringWriter(1024);
			for (int c = reader.read(); c != -1; c = reader.read())
			{
				sw.write(c);
			}
			
			return sw.toString();
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
		finally
		{
			Utils.closeNoExceptions(reader);
		}
	}
	
	
	public static void writeFile(String name, String contents) throws IOException
	{
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(name);
			writer.write(contents);
		}
		finally
		{
			Utils.closeNoExceptions(writer);
		}
	}


	public static void closeNoExceptions(FileWriter writer)
	{
		if (null == writer)
			return;
		
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			; // the point of this method is to avoid throwing exceptions
		}
	}
	
	/**
	 * Find the enum tag that matches a string without regards to case.
	 * 
	 * <P>
	 * This method is intended to be used like so:
	 * </P>
	 * 
	 * <P>
	 * <CODE>
	 * <PRE>
	 * enum Foo {
	 *     One,Two,Three,Four;
	 *     public static Foo toValueIgnoreCase(String s) {
	 *         return (Foo) Utils.toValueIgnoreCase(Foo.values(), s);
	 *     }
	 * }
	 * </PRE>
	 * </CODE>
	 * </P>
	 * 
	 * 
	 * @param values The enum tag values.
	 * @param s The string we are trying to convert.
	 * @return The matching enum tag.
	 * @throws RuntimeException If there is no match.
	 */
	public static Object toValueIgnoreCase(Object[] values, String s)
	{
		Object result = matchIgnoreCase(values, s);
		
		if (null == result)
		{
			String msg = s + " does not matching any tag name";
			throw new RuntimeException(msg);
		}
		
		return result;
	}
	
	
	public static Object matchIgnoreCase(Object[] values, String s)
	{
		for (Object tag : values)
		{
			if (s.equalsIgnoreCase(tag.toString()))
				return tag;
		}
		
		return null;
	}


	public static void closeNoExceptions(OutputStream ostream)
	{
		try
		{
			ostream.close();
		}
		catch (IOException e)
		{
			;
		}
	}
}
