package com.jhu.clueless.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * Defines a Validator test.
 *
 * @author Clint Pitzak
 *
 */
public class ValidatorTest {

	/**
	 * Tess whether or not the ipAddress is valid.
	 */
	@Test
	public void isIpAddressValidTest() {
		assertThat(Validator.isIpAddressValid(null), is(false));
		assertThat(Validator.isIpAddressValid("lkajdfl"), is(false));
		assertThat(Validator.isIpAddressValid("192.1.13"), is(false));
		assertThat(Validator.isIpAddressValid("192.2.1.1"), is(true));
	}

}
