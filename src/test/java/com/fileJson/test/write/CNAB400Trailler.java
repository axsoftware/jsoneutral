package com.fileJson.test.write;

public class CNAB400Trailler {

	public static String ID = "TRAILLER";
	
	// 001 001 TIPO DE REGISTRO: 9 (constante)
	private String recordType;

	// 028 040 TOTAL GERAL OU SOMATÓRIO DOS VALORES DOS TÍTULOS
	private String total;

	// 395 400 NÚMERO SEQUENCIAL DO REGISTRO
	private String sequenceRecord;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSequenceRecord() {
		return sequenceRecord;
	}

	public void setSequenceRecord(String sequenceRecord) {
		this.sequenceRecord = sequenceRecord;
	}

}
