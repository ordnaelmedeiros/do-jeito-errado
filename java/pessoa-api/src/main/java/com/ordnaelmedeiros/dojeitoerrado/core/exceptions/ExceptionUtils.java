package com.ordnaelmedeiros.dojeitoerrado.core.exceptions;

import javax.ws.rs.NotFoundException;

public class ExceptionUtils {

	public static void throwNotFoundException() {
		throw new NotFoundException();
	}
	
}
