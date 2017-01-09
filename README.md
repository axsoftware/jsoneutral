# jsoneutral [![Build Status](https://travis-ci.org/axsoftware/jsoneutral.svg?branch=develop)](https://travis-ci.org/axsoftware/jsoneutral)


# JSONeutral

Simple text builder in Java using json file. Below an simple example.

Example

### Reader

sample.json

```json
[
	{ 
		"id": "CUSTOMER",	
		"fragmentsMetadata": [
			{
				"start"	: "1",
				"end"	: "20",
				"key"	: "name"
			},
			{
				"start"	: "21",
				"end"	: "40",
				"key"	: "email"
			}	
		]
	}
]
```

Customer.java

```java
public class Customer {

	public static final String IDENTIFIER = "CUSTOMER";

	private String name;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
```

sample.txt 

```text
SAMPLE NAME        TESTE@TESTE.COM.BR
```


ReaderFileTest.java

```java

public class ReaderFileTest {

	
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
		
	}
}

```


