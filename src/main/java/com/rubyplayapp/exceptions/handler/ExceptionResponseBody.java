package com.rubyplayapp.exceptions.handler;

class ExceptionResponseBody {

	private final String exceptionMassage;

	ExceptionResponseBody(final String exceptionMassage) {
		this.exceptionMassage = exceptionMassage;
	}

	public String getExceptionMassage() {
		return this.exceptionMassage;
	}
}
