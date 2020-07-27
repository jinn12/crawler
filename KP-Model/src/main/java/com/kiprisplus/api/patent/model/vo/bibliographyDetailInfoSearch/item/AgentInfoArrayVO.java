package com.kiprisplus.api.patent.model.vo.bibliographyDetailInfoSearch.item;

import java.util.ArrayList;
import java.util.List;

public class AgentInfoArrayVO {
	private List<AgentInfoVO> agentInfo = new ArrayList<AgentInfoVO>();

	public List<AgentInfoVO> getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(List<AgentInfoVO> agentInfo) {
		this.agentInfo = agentInfo;
	}

}
