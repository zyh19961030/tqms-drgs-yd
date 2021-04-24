package com.qu.constant;

import com.google.common.collect.Lists;

import java.util.List;

public class QSingleDiseaseTakeConstant {

	public static final Integer STATUS_WAIT_WRITE = 0;
	public static final Integer STATUS_WAIT_WRITE_GOING = 1;
	public static final Integer STATUS_WAIT_UPLOAD = 2;
	public static final Integer STATUS_REJECT = 3;
	public static final Integer STATUS_PASS_WAIT_UPLOAD = 4;
	public static final Integer STATUS_NO_NEED = 8;

	public static final Integer REPORT_STATUS_NO_NEED = 0;

	public static final List<Integer> STATUS_REJECT_AND_COUNTRY_REJECT = Lists.newArrayList(3,7);



}
