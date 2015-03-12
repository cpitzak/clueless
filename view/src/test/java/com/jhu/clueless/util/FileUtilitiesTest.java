package com.jhu.clueless.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * Defines a file utilities test.
 *
 * @author Clint Pitzak
 *
 */
public class FileUtilitiesTest {

	/**
	 * Tests the get icon method.
	 */
	@Test
	public void getIconTest() {
		assertThat(FileUtilities.getIcon("CLUELESS.png"), notNullValue());
		assertThat(FileUtilities.getIcon("junk"), nullValue());
		assertThat(FileUtilities.getIcon(null), nullValue());
	}

}
