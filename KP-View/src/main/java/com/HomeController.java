package com;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kiprisplus.api.patent.job.PatentJobService;
import com.kiprisplus.api.trademark.job.TrademarkJobService;



@Controller
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
public class HomeController implements EnvironmentAware {
	 private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.env = environment;
	}
	@Autowired
	TrademarkJobService trademarkJobService;

	@Autowired
	PatentJobService patentJobService;


	@RequestMapping(value = { "/tmall"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String tmall(ModelMap model, HttpServletRequest request) throws Exception {


		trademarkJobService.kipirsPlusTrademarkByBulkBatchJob();


		return null;
	}
	@RequestMapping(value = { "/tmall2"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String execKiprisPlusTrademarkUpdateBatch(ModelMap model, HttpServletRequest request) throws Exception {

		trademarkJobService.kipirsPlusTrademarkByWeeklyBatchJob();

		return null;
	}


	@RequestMapping(value = { "/patentbatch"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String execKiprisPlusPatentOnetimeBatch(ModelMap model, HttpServletRequest request) throws Exception {

		patentJobService.kipirsPlusPatentByBulkBatchJob();

		return null;
	}
	@RequestMapping(value = { "/patentbatch2"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String execKiprisPlusPatentUpdateBatch(ModelMap model, HttpServletRequest request) throws Exception {

		patentJobService.kipirsPlusPatentByWeeklyBatchJob();

		return null;
	}
}
