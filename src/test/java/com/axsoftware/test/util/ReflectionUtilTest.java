package com.axsoftware.test.util;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.axsoftware.jsoneutral.file.util.ReflectionUtil;

/**
 * Execute tests in ReflectionUtil  
 * @author andrei
 *
 */
public class ReflectionUtilTest {

	/**
	 * Check if ReflectionUtil.getObjectFields return 2 Fields object
	 * ObjectChild is an extension of ObjectMaster
	 */
	@Test
	public void getFields(){
		
		final ObjectChild child = new ObjectChild(1L, "Child object");
		
		final Field[] fields = ReflectionUtil.getObjectFields(child.getClass());
		
		Assert.assertTrue(fields.length == 2);
	}
}
