package com.axsoftware.test.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.axsoftware.jsoneutral.file.JsonDataLine;
import com.axsoftware.jsoneutral.file.JsonTextFile;
import com.axsoftware.jsoneutral.file.reader.FileJsonReader;
import com.axsoftware.jsoneutral.file.reader.FileJsonReaderImpl;
import com.axsoftware.jsoneutral.file.reader.FileJsonReaderProcessor;

public class ReaderFileTest {

	
	@Test
	public void createSimpleObject(){
		
		final List<Customer> customers = new ArrayList<>();
		
		final FileJsonReader reader = new FileJsonReaderImpl();
		reader.execute(new File(this.getClass().getResource("/sample.txt").getPath()), "sample.json", new FileJsonReaderProcessor(){

			@Override
			public void process(JsonTextFile structuredTextFile) throws IOException {

				JsonDataLine line = null;
				while (structuredTextFile.hasNext()) {
					line = structuredTextFile.next();
					customers.add(line.deserialize(Customer.IDENTIFIER, Customer.class));
				}
				
			}
			
		});
		
		Assert.assertTrue(customers.size() > 0);
	}
}
