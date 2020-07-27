package com.kiprisplus.api.patent.job.impl;

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
import com.kiprisplus.api.patent.job.PatentJobService;
import com.kiprisplus.api.patent.service.KiprisPlusPatentService;

@Service
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
public class PatentJobServiceImpl implements PatentJobService, EnvironmentAware {
	private Environment env;

	@Autowired
	KiprisPlusPatentService kiprisPlusPatentService;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
	@Override
	@Schedules({@Scheduled(cron = "${kp.patent.schedules.bulk.cron}")})
	public void kipirsPlusPatentByBulkBatchJob() {
		String uuid = UUID.randomUUID().toString();//배치 키생성

		Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼연단위 배치 실행▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼");

		try {
			kiprisPlusPatentService.execKiprisPlusPatentBulkBatch(uuid);
		} catch(Exception e) {
			e.printStackTrace();
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################오류가 발생하여 배치가 종료되었습니다.");
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + e.getMessage());
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + ExceptionUtils.exceptionToString(e));
			e.printStackTrace();
		}
		Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲연단위 배치 완료▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\r\n\r\n");

	}

	@Override
	@Schedules({@Scheduled(cron = "${kp.patent.schedules.update.cron}")})
	public void kipirsPlusPatentByWeeklyBatchJob() {
		String uuid = UUID.randomUUID().toString();//배치 키생성
		Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼주단위 배치 실행▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼");

		try {
			kiprisPlusPatentService.execKiprisPlusPatentUpdateBatch(uuid);
		} catch(Exception e) {
			e.printStackTrace();
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "##################################오류가 발생하여 배치가 종료되었습니다.");
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + e.getMessage());
			Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + ExceptionUtils.exceptionToString(e));
			e.printStackTrace();
		}
		Log.PATENT_BATCH_LOGGER.error("[" + uuid + "]" + "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲주단위 배치 완료▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\r\n\r\n");

	}
}
