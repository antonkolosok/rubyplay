package com.rubyplayapp.utils;

import com.rubyplayapp.domain.Visitor;

public class VisitorTestData {

	public static final int VISITOR_ID = 1;
	public static final String VISITOR_NAME = "visitorName";

	public static Visitor preparedVisitor() {
		final Visitor visitor = new Visitor();
		visitor.setId(VISITOR_ID);
		visitor.setName(VISITOR_NAME);
		return visitor;
	}

}
