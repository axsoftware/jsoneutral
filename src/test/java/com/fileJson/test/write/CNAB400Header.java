package com.fileJson.test.write;

public class CNAB400Header {

	public static String ID = "HEADER";
	
	// 001 009 01REMESSA (constante)
	private String recordType;

	// 027 039 CÓDIGO DE CEDENTE
	private String code;

	// 047 076 NOME DA EMPRESA
	private String name;

	// 077 087 041BANRISUL (constante)
	private String bankCode;

	// 095 100 DATA DE GRAVAÇÃO DO ARQUIVO
	private String dateFileStore;

	// 110 113 CÓDIGO DO SERVIÇO
	private String serviceCode;

	// 115 115 TIPO DE PROCESSAMENTO
	private String processType;

	// 117 126 CÓDIGO DO CLIENTE NO OFFICE BANKING
	private String officeBankCode;

	// 395 400 000001 (constante)
	private String sequenceRecord;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getDateFileStore() {
		return dateFileStore;
	}

	public void setDateFileStore(String dateFileStore) {
		this.dateFileStore = dateFileStore;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getOfficeBankCode() {
		return officeBankCode;
	}

	public void setOfficeBankCode(String officeBankCode) {
		this.officeBankCode = officeBankCode;
	}

	public String getSequenceRecord() {
		return sequenceRecord;
	}

	public void setSequenceRecord(String sequenceRecord) {
		this.sequenceRecord = sequenceRecord;
	}

}
