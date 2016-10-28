package com.axsoftware.jsoneutral.file.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Validates data string treatment
 */
public class StringDataUtilTest {
	@Test
	public void removeDiacritics() throws Exception {
		final String email = "TEST@MAIL.COM";
		assertEquals(email, StringDataUtil.removeDiacritics(email));
	}

}