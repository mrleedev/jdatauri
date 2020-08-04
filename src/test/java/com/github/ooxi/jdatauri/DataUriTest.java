/**
 * Copyright (c) 2013 ooxi
 *     https://github.com/ooxi/jdatauri
 * 
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 *  1. The origin of this software must not be misrepresented; you must not
 *     claim that you wrote the original software. If you use this software in a
 *     product, an acknowledgment in the product documentation would be
 *     appreciated but is not required.
 * 
 *  2. Altered source versions must be plainly marked as such, and must not be
 *     misrepresented as being the original software.
 * 
 *  3. This notice may not be removed or altered from any source distribution.
 */
package com.github.ooxi.jdatauri;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.text.html.Option;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author ooxi
 */
public class DataUriTest {
	
	private final Charset UTF_8 = Charset.forName("UTF-8");
	
	
	
	@Test
	public void testSimple() {
		final String test = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		DataUri duri = DataUri.parse(test, UTF_8);
		
		assertEquals("image/gif", duri.getMime());
		assertEquals(Optional.empty(), duri.getCharset());
		assertEquals(Optional.empty(), duri.getFilename());
		assertEquals(Optional.empty(), duri.getContentDisposition());
		assertArrayEquals(new byte[] {71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59}, duri.getData());
	}
	
	
	
	@Test
	public void testSimpleConstructor() {
		final String EXPECTED_MIME = "text/plain";
		final Charset EXPECTED_CHARSET = Charset.forName("ISO-8859-15");
		final byte[] EXPECTED_DATA = new byte[] {1, 2, 3};
		
		DataUri duri = new DataUri(EXPECTED_MIME, EXPECTED_CHARSET, EXPECTED_DATA);
		
		assertEquals(EXPECTED_MIME, duri.getMime());
		assertEquals(Optional.of(EXPECTED_CHARSET), duri.getCharset());
		assertEquals(Optional.empty(), duri.getFilename());
		assertEquals(Optional.empty(), duri.getContentDisposition());
		assertArrayEquals(EXPECTED_DATA, duri.getData());
	}
	
	
	
	@Test
	public void testExtendedConstructor() {
		final String EXPECTED_MIME = "text/plain";
		final Charset EXPECTED_CHARSET = Charset.forName("ISO-8859-15");
		final String EXPECTED_FILENAME = "test.txt";
		final String EXPECTED_CONTENT_DISPOSITION = "inline";
		final byte[] EXPECTED_DATA = new byte[] {1, 2, 3};
		
		DataUri duri = new DataUri(
			EXPECTED_MIME,
			EXPECTED_CHARSET,
			EXPECTED_FILENAME,
			EXPECTED_CONTENT_DISPOSITION,
			EXPECTED_DATA
		);
		
		assertEquals(EXPECTED_MIME, duri.getMime());
		assertEquals(Optional.of(EXPECTED_CHARSET), duri.getCharset());
		assertEquals(Optional.of(EXPECTED_FILENAME), duri.getFilename());
		assertEquals(Optional.of(EXPECTED_CONTENT_DISPOSITION), duri.getContentDisposition());
		assertArrayEquals(EXPECTED_DATA, duri.getData());
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testDisallowMimeNull() {
		new DataUri(null, UTF_8, new byte[] {});
	}



	@Test(expected = IllegalArgumentException.class)
	public void testDisallowDataNull() {
		new DataUri("text/plain", UTF_8, null);
	}
	
	
	
	@Test
	public void testStartWithDataSchema() {
		try {
			DataUri.parse("dato:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==", UTF_8);
			Assert.fail("URI must start with data:");
		} catch (IllegalArgumentException e) {
			// Pass
		}
	}
	
	
	
	@Test
	public void testCaseInsensitivedataSchema() {
		final String test = "DaTa:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		DataUri duri = DataUri.parse(test, UTF_8);
		
		assertEquals("image/gif", duri.getMime());
		assertEquals(Optional.empty(), duri.getCharset());
		assertEquals(Optional.empty(), duri.getFilename());
		assertEquals(Optional.empty(), duri.getContentDisposition());
		assertArrayEquals(new byte[] {71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59}, duri.getData());
	}
	
	
	
	@Test
	public void testEquals() {
		final String test = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		DataUri duri = DataUri.parse(test, UTF_8);
		
		DataUri equal = new DataUri("image/gif", null, new byte[] {71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59});
		DataUri notEqual = new DataUri("image/gif", null, new byte[] {72, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59});
		
		if (!duri.equals(equal)) {
			Assert.fail("Equal returns false on equal instances");
		}
		if (duri.equals(notEqual)) {
			Assert.fail("Equal returns true on unequal instances");
		}
	}
	
	
	
	@Test
	public void testHashcode() {
		final String test = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		DataUri duri = DataUri.parse(test, UTF_8);
		
		DataUri equal = new DataUri("image/gif", null, new byte[] {71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59});
		DataUri notEqual = new DataUri("image/gif", null, new byte[] {72, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59});
		
		if (duri.hashCode() != equal.hashCode()) {
			Assert.fail("hashCode different on equal instances");
		}
		if (duri.hashCode() == notEqual.hashCode()) {
			Assert.fail("hashCode equal on different instances, doesn't have to be an error but is highly likely");
		}
	}
	
	
	
	@Test
	public void testMustContainComma() {
		try {
			DataUri.parse("DaTa:image/gif;base64;R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==", UTF_8);
			Assert.fail("URI must contain a comma");
		} catch (IllegalArgumentException e) {
			// Pass
		}
	}
	
	
	
	@Test
	public void testOptions() {
		final String test = "data:image/gif;charset=utf-8;filename=test.txt;content-disposition=inline;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		DataUri duri = DataUri.parse(test, UTF_8);
		
		assertEquals("image/gif", duri.getMime());
		assertEquals(Optional.of(Charset.forName("UTF-8")), duri.getCharset());
		assertEquals(Optional.of("test.txt"), duri.getFilename());
		assertEquals(Optional.of("inline"), duri.getContentDisposition());
		assertArrayEquals(new byte[] {71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59}, duri.getData());
	}



	@Test
	public void testToString() {
		final String[] testStrings = {
				"data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==",
				"data:image/gif;charset=UTF-8;content-disposition=inline;filename=test.txt;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==",
				"data:text/html;base64,dGVzdA==",
				"data:text/html;charset=windows-1250;base64,dGVzdA==",
				"data:text/html;filename=test.txt;base64,dGVzdA==",
				"data:text/html;content-disposition=attachment;base64,dGVzdA==",
				"data:text/html;content-disposition=attachment;filename=t.txt;base64,dGVzdA=="
		};

		for (final String testString : testStrings) {
			assertEquals(testString, DataUri.parse(testString, UTF_8).toString());
		}
	}

	@Test
	public void testPlusCharacter() {
		// spaces shouldn't turn into pluses (see https://github.com/ooxi/jdatauri/issues/10)
		DataUri duri = DataUri.parse("data:text/plain;charset=utf-8,Hello%2C%20how%20do%20you%20do%3F", UTF_8);
		assertEquals("Hello, how do you do?", new String(duri.getData(), duri.getCharset().get()));

		// plus in a mime type isn't decoded to space; pluses and spaces in data are decoded correctly.
		duri = DataUri.parse("data:application/atom+xml;charset=utf-8,%3Ca%3E1%2B1%3D2%20isn%27t%20it%3F%3C%2Fa%3E", UTF_8);
		assertEquals("<a>1+1=2 isn't it?</a>", new String(duri.getData(), duri.getCharset().get()));
		assertEquals("application/atom+xml", duri.getMime());
	}
}
