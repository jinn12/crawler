package com.kiprisplus.api.trademark.job.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import com.cmn.cmn.log.Log;
import com.cmn.utils.ExceptionUtils;
import com.kiprisplus.api.trademark.job.TrademarkJobService;
import com.kiprisplus.api.trademark.service.KiprisPlusTrademarkService;

@Service
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
public class TrademarkJobServiceImpl implements EnvironmentAware, TrademarkJobService {
    private Environment env;

    @Autowired
    KiprisPlusTrademarkService kiprisPlusTrademarkService;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	public void getKipirsPlusTrademarkFromYear() {

	}

	@Override
	@Schedules({@Scheduled(cron = "${kp.trademark.schedules.bulk.cron}")})
	public void kipirsPlusTrademarkByBulkBatchJob() {
		String uuid = UUID.randomUUID().toString();//배치 키생성

		Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼연단위 배치 실행▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼");

		try {
			kiprisPlusTrademarkService.execKiprisPlusTrademarkBulkBatch(uuid);
		} catch(Exception e) {
			e.printStackTrace();
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################오류가 발생하여 배치가 종료되었습니다.");
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + e.getMessage());
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + ExceptionUtils.exceptionToString(e));
			e.printStackTrace();
		}
		Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲연단위 배치 완료▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\r\n\r\n");
	}

	@Override
	@Schedules({@Scheduled(cron = "${kp.trademark.schedules.update.cron}")})
	public void kipirsPlusTrademarkByWeeklyBatchJob() {
		String uuid = UUID.randomUUID().toString();//배치 키생성
		Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼주단위 배치 실행▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼");

		try {
			kiprisPlusTrademarkService.execKiprisPlusTrademarkUpdateBatch(uuid);
		} catch(Exception e) {
			e.printStackTrace();
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "##################################오류가 발생하여 배치가 종료되었습니다.");
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + e.getMessage());
			Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + ExceptionUtils.exceptionToString(e));
			e.printStackTrace();
		}
		Log.TRADEMARK_BATCH_LOGGER.error("[" + uuid + "]" + "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲주단위 배치 완료▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\r\n\r\n");
	}


}
