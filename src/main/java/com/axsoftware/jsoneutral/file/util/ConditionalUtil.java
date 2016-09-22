package com.axsoftware.jsoneutral.file.util;

import java.util.Arrays;

public final class ConditionalUtil {

	private ConditionalUtil() {

	}

	/**
	 * Retorna o valor o primeiro elemento não-nulo
	 *
	 * @param ts
	 *            Type Stream recebe N parametros e trata-os retornando o valor
	 *            do primeiro que não estiver nulo.
	 */
	@SafeVarargs
	public static <T> T coalesce(T... ts) {
		return Arrays.stream(ts).map(m -> m).filter(t -> t != null).findFirst().orElse(null);
	}
}