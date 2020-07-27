package com.rnd.solstice.model.vo;

import java.util.ArrayList;
import java.util.List;

public class Tb20310TmBiblioListVO {
	List<Tb20300TmDbVO> tb20310TmBiblioList = new ArrayList<Tb20300TmDbVO>();

	public List<Tb20300TmDbVO> getTb20310TmBiblioList() {
		return tb20310TmBiblioList;
	}

	public void setTb20310TmBiblioList(List<Tb20300TmDbVO> tb20310TmBiblioList) {
		this.tb20310TmBiblioList = tb20310TmBiblioList;
	}

	public void addBiblioList(Tb20300TmDbVO vo ) {
		this.tb20310TmBiblioList.add(vo);
	}

}
