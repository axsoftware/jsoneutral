package com.axsoftware.jsoneutral.file.util;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringDataUtil {

	private static final String DD_MM_YY 		= "ddMMyy";
	private static final String RX_VALID_TEXT 	= "[^a-zA-Z0-9,_\\.\\-\\s+]";
	private static final String RX_DIACRITICS 	= "\\p{InCombiningDiacriticalMarks}+";

	private static String pad(String s, int n, String fill, boolean right) {
		final String pad = right ? "%-" : "%";
		String rpl = String.format(pad + n + "s", s);
		if(fill != null){
			rpl = rpl.replace(" ", fill);
		}
		return rpl;
	}

	public static String rightPad(String s, int n) {
		return rightPad(s, n, null);
	}
	
	public static String rightPad(String s, int n, String fill) {
		return pad(s, n, fill, true);
	}
	
	public static String leftPad(String s, int n, String fill) {
		return pad(s, n, fill, false);
	}	
	
	public static String formatDate(final Date dateInput, final String format){
		return new SimpleDateFormat(format).format(dateInput);
	}
	
	public static String formatDate(final Date dateInput){
		return formatDate(dateInput, DD_MM_YY);
	}
	
	public static String formatDate(){
		return formatDate(new Date(), DD_MM_YY);
	}
	
	public static String formatDecimal(String value){
		return String.format("%01.0f", new BigDecimal(value).doubleValue() * 100);
	}

	public static String removeDiacritics(String str) {
	    final String normalize = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    final Pattern pattern = Pattern.compile(RX_DIACRITICS);
	    String text = pattern.matcher(normalize).replaceAll("");
	    text = text.replaceAll(RX_VALID_TEXT, ""); 
	    return text.toUpperCase();
	}
}
