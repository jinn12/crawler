package com.cmn.cmn.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	public static Logger ERROR_LOGGER = LoggerFactory.getLogger("kpErrorLogger");
	public static Logger TRADEMARK_BATCH_LOGGER = LoggerFactory.getLogger("trademarkBatchLogger");
	public static Logger PATENT_BATCH_LOGGER = LoggerFactory.getLogger("patentBatchLogger");

}
