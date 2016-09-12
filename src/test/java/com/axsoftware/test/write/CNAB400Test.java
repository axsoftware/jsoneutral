package com.axsoftware.test.write;

import static com.axsoftware.jsoneutral.file.util.StringDataUtil.formatDate;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.formatDecimal;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.leftPad;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.axsoftware.jsoneutral.file.writer.FileJsonWriter;
import com.axsoftware.jsoneutral.file.writer.FileJsonWriterImpl;

public class CNAB400Test {

	private static final String CONFIG_JSON = "cnab400.json";

	private Integer sequence = 0;
	
	private BigDecimal total = BigDecimal.ZERO;
	
	
	@Test
	public void writeObject() throws Exception {

		final FileJsonWriter writer = new FileJsonWriterImpl();

		final List<String> records = new ArrayList<>();	
		
		final CNAB400Header header = createHeader();
		String headerData = writer.execute(header, CONFIG_JSON, CNAB400Header.ID);
		records.add(headerData);

		final List<CNAB400Transaction> transactions = createTransaction();
		Collection<String> list = writer.execute(transactions, CONFIG_JSON, CNAB400Transaction.ID);
		for (String tr : list) {
			records.add(tr);
		}
		
		final CNAB400Trailler trailer = createTrailler();
		String trailerData = writer.execute(trailer, CONFIG_JSON, CNAB400Trailler.ID);
		records.add(trailerData);

		final File file = writer.generateFile(records, "");
		
		Assert.assertTrue(file.exists());
	}

	private CNAB400Header createHeader() {

		final CNAB400Header header = new CNAB400Header();
		header.setRecordType("01REMESSA");
		header.setCode("0415000044020");
		header.setName("Empresa Teste LTDA - EP");
		header.setBankCode("041BANRISUL");
		header.setDateFileStore(formatDate());
		header.setSequenceRecord(getSequence());
		
		return header;
	}

	private List<CNAB400Transaction> createTransaction() {

		final List<CNAB400Transaction> list = new ArrayList<>();

		final BigDecimal value = new BigDecimal("171.19"); 
		
		total = total.add(value);
		
		final String id = "00967499044";
		
		final String expireDate = formatDate(new Date());
		
		final String idDocument = "1047MEE120920160049";
		
		final CNAB400Transaction transaction = new CNAB400Transaction();
		
		transaction.setRecordType("1");
		transaction.setCode("0415000044020");
		transaction.setIdDocument(idDocument);
		transaction.setOurNumber(leftPad("254", 10, "0"));
		transaction.setWalletType("1");
		transaction.setOccurrenceCode("01");
		transaction.setYourNumber("0000155-  ");
		transaction.setExpireDate(expireDate);
		transaction.setDocumentValue(formatValue(value));
		transaction.setCodeCollectorBank("041");
		transaction.setDocumentType("08");
		transaction.setAcceptCode("A");
		transaction.setDateRecord(formatDate());
		transaction.setInst1Code("09");
		transaction.setDateDiscount("000000");
		transaction.setDiscountValue("0000000000000");
		transaction.setxTaxPay("000000");
		transaction.setXiof("00");
		transaction.setXzeros("000000");
		transaction.setReductionValue("00000000000000");
		transaction.setInscType(id.length() == 11 ? "01" : "02");
		transaction.setInscNumber(leftPad(id, 14, "0"));
		transaction.setPayerName("JOSÉ DA SILVA SAURO");
		transaction.setPayerAddress("RUA DA FRENTE,1020 BAIRRO");
		transaction.setPostCode("93080250");
		transaction.setCityPayer("PORTO ALEGRE");
		transaction.setUf("RS");
		transaction.setProtestDays("03");
		transaction.setSequenceRecord(getSequence());
		
		list.add(transaction);

		return list;
	}

	private CNAB400Trailler createTrailler() {

		final CNAB400Trailler trailer = new CNAB400Trailler();
		trailer.setRecordType("9");
		trailer.setTotal(formatValue(total));
		trailer.setSequenceRecord(getSequence());

		return trailer;
	}

	private String getSequence() {
		sequence++;
		return leftPad(sequence.toString(), 6, "0");
	}
	
	private String formatValue(final BigDecimal dataValue){
		return leftPad(formatDecimal(dataValue.toString()), 13, "0");
	}
}