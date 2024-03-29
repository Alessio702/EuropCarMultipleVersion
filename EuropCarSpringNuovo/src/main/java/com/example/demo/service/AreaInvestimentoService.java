package com.example.demo.service;

import java.util.List;

import com.example.demo.model.AreaInvestimento;

public interface AreaInvestimentoService {
	
	public List<AreaInvestimento> getAllAreeInvestimento();
	
	public List<AreaInvestimento> getAreePerAnno(Integer idAnno);
	
	public AreaInvestimento getAreaInvestimentoById(Integer idArea);
	
	public AreaInvestimento saveOrUpdate(AreaInvestimento oArea);
	
	public void deleteArea(Integer idArea);

}
