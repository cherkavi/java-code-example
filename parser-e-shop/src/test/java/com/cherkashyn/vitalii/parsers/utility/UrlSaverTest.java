package com.cherkashyn.vitalii.parsers.utility;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class UrlSaverTest {
	@Test
	public void testSave() throws IOException{
		Assert.assertTrue(UrlSaver.saveContentToDisk("http://i1.rozetka.ua/logos/0/3.png", "c:\\temp\\out.png")>0);
	}
}
