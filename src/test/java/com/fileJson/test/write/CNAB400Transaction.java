package com.fileJson.test.write;

public class CNAB400Transaction {
	
	public static String ID = "TRANSACTION";

	// 001 001 TIPO DE REGISTRO: 1 (constante)
	private String recordType;

	// 018 030 CÓDIGO DE CEDENTE
	private String code;

	// 038 062 IDENTIFICAÇÃO DO TÍTULO PARA O BENEFICIÁRIO
	private String idDocument;

	// 063 072 IDENTIFICAÇÃO DO TÍTULO PARA O BANCO (NOSSO NÚMERO)
	private String ourNumber;

	// 073 104 MENSAGEM NO BLOQUETO
	private String message;

	// 108 108 TIPO DE CARTEIRA
	private String walletType;

	// 109 110 CÓDIGO DE OCORRÊNCIA
	private String occurrenceCode;

	// 111 120 SEU NÚMERO
	private String yourNumber;

	// 121 126 DATA DE VENCIMENTO DO TÍTULO
	private String expireDate;

	// 127 139 VALOR DO TÍTULO
	private String documentValue;

	// 140 142 BANCO COBRADOR: 041 (constante)
	private String codeCollectorBank;

	// 148 149 TIPO DE DOCUMENTO
	private String documentType;

	// 150 150 CÓDIGO DE ACEITE
	private String acceptCode;

	// 151 156 DATA DA EMISSÃO DO TÍTULO
	private String dateRecord;

	// 157 158 CÓDIGO DA 1ª INSTRUÇÃO
	private String inst1Code;

	// 159 160 CÓDIGO DA 2ª INSTRUÇÃO
	private String inst2Code;

	// 161 161 CÓDIGO DE MORA
	private String moraCode;

	// 162 173 VALOR AO DIA OU TAXA MENSAL DE JUROS
	private String tax;

	// 174 179 DATA PARA CONCESSÃO DO DESCONTO
	private String dateDiscount;

	// 180 192 VALOR DO DESCONTO A SER CONCEDIDO
	private String discountValue;

	// 193 205 VALOR IOF
	private String iofValue;

	// 193 197 CARTEIRA X – TAXA DE JUROS DO PAGADOR
	private String xTaxPay;

	// 198 199 CARTEIRA X – IOF
	private String xiof;

	// 200 205 CARTEIRA X – ZEROS
	private String xzeros;

	// 206 218 VALOR DO ABATIMENTO
	private String reductionValue;

	// 219 220 TIPO DE INSCRIÇÃO DO PAGADOR
	private String inscType;

	// 221 234 NÚMERO DE INSCRIÇÃO DO PAGADOR NO MF
	private String inscNumber;

	// 235 269 NOME DO PAGADOR
	private String payerName;

	// 275 314 ENDEREÇO DO PAGADOR
	private String payerAddress;

	// 322 324 TAXA PARA MULTA APÓS O VENCIMENTO
	private String taxNote;

	// 325 326 NÚMERO DE DIAS PARA MULTA APÓS O VENCIMENTO
	private String daysAfterNote;

	// 327 334 CEP
	private String postCode;

	// 335 349 CIDADE DO PAGADOR (PRAÇA DE COBRANÇA)
	private String cityPayer;

	// 350 351 UF – UNIDADE DA FEDERAÇÃO
	private String uf;

	// 352 355 TAXA AO DIA PARA PAGAMENTO ANTECIPADO
	private String earlyDayTax;

	// 358 369 VALOR PARA CÁLCULO DO DESCONTO
	private String discountCalcValue;

	// 370 371 NÚMERO DE DIAS PARA PROTESTO OU DE DEVOLUÇÃO AUTOMÁTICA
	private String protestDays;

	// 395 400 NÚMERO SEQUENCIAL DO REGISTRO
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

	public String getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(String idDocument) {
		this.idDocument = idDocument;
	}

	public String getOurNumber() {
		return ourNumber;
	}

	public void setOurNumber(String ourNumber) {
		this.ourNumber = ourNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public String getOccurrenceCode() {
		return occurrenceCode;
	}

	public void setOccurrenceCode(String occurrenceCode) {
		this.occurrenceCode = occurrenceCode;
	}

	public String getYourNumber() {
		return yourNumber;
	}

	public void setYourNumber(String yourNumber) {
		this.yourNumber = yourNumber;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCodeCollectorBank() {
		return codeCollectorBank;
	}

	public void setCodeCollectorBank(String codeCollectorBank) {
		this.codeCollectorBank = codeCollectorBank;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getAcceptCode() {
		return acceptCode;
	}

	public void setAcceptCode(String acceptCode) {
		this.acceptCode = acceptCode;
	}

	public String getDateRecord() {
		return dateRecord;
	}

	public void setDateRecord(String dateRecord) {
		this.dateRecord = dateRecord;
	}

	public String getInst1Code() {
		return inst1Code;
	}

	public void setInst1Code(String inst1Code) {
		this.inst1Code = inst1Code;
	}

	public String getInst2Code() {
		return inst2Code;
	}

	public void setInst2Code(String inst2Code) {
		this.inst2Code = inst2Code;
	}

	public String getMoraCode() {
		return moraCode;
	}

	public void setMoraCode(String moraCode) {
		this.moraCode = moraCode;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getDateDiscount() {
		return dateDiscount;
	}

	public void setDateDiscount(String dateDiscount) {
		this.dateDiscount = dateDiscount;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getIofValue() {
		return iofValue;
	}

	public void setIofValue(String iofValue) {
		this.iofValue = iofValue;
	}

	public String getxTaxPay() {
		return xTaxPay;
	}

	public void setxTaxPay(String xTaxPay) {
		this.xTaxPay = xTaxPay;
	}

	public String getXiof() {
		return xiof;
	}

	public void setXiof(String xiof) {
		this.xiof = xiof;
	}

	public String getXzeros() {
		return xzeros;
	}

	public void setXzeros(String xzeros) {
		this.xzeros = xzeros;
	}

	public String getReductionValue() {
		return reductionValue;
	}

	public void setReductionValue(String reductionValue) {
		this.reductionValue = reductionValue;
	}

	public String getInscType() {
		return inscType;
	}

	public void setInscType(String inscType) {
		this.inscType = inscType;
	}

	public String getInscNumber() {
		return inscNumber;
	}

	public void setInscNumber(String inscNumber) {
		this.inscNumber = inscNumber;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerAddress() {
		return payerAddress;
	}

	public void setPayerAddress(String payerAddress) {
		this.payerAddress = payerAddress;
	}

	public String getTaxNote() {
		return taxNote;
	}

	public void setTaxNote(String taxNote) {
		this.taxNote = taxNote;
	}

	public String getDaysAfterNote() {
		return daysAfterNote;
	}

	public void setDaysAfterNote(String daysAfterNote) {
		this.daysAfterNote = daysAfterNote;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCityPayer() {
		return cityPayer;
	}

	public void setCityPayer(String cityPayer) {
		this.cityPayer = cityPayer;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEarlyDayTax() {
		return earlyDayTax;
	}

	public void setEarlyDayTax(String earlyDayTax) {
		this.earlyDayTax = earlyDayTax;
	}

	public String getDiscountCalcValue() {
		return discountCalcValue;
	}

	public void setDiscountCalcValue(String discountCalcValue) {
		this.discountCalcValue = discountCalcValue;
	}

	public String getProtestDays() {
		return protestDays;
	}

	public void setProtestDays(String protestDays) {
		this.protestDays = protestDays;
	}

	public String getSequenceRecord() {
		return sequenceRecord;
	}

	public void setSequenceRecord(String sequenceRecord) {
		this.sequenceRecord = sequenceRecord;
	}

	public String getDocumentValue() {
		return documentValue;
	}

	public void setDocumentValue(String documentValue) {
		this.documentValue = documentValue;
	}

}
