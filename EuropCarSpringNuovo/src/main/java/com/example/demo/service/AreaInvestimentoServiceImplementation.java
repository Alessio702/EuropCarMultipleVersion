package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AreaInvestimento;
import com.example.demo.repository.AreaInvestimentoRepository;

@Service
@Transactional
public class AreaInvestimentoServiceImplementation implements AreaInvestimentoService {
	
	@Autowired
	AreaInvestimentoRepository arearep;

	@Override
	public List<AreaInvestimento> getAllAreeInvestimento() {
		return (List<AreaInvestimento>) arearep.findAll();
	}
	
	

	@Override
	public AreaInvestimento getAreaInvestimentoById(Integer idArea) {
		return arearep.findById(idArea).get();
	}

	@Override
	public AreaInvestimento saveOrUpdate(AreaInvestimento oArea) {
		return arearep.save(oArea);
	}

	@Override
	public void deleteArea(Integer idArea) {
		arearep.deleteById(idArea);
	}


	@Override
	public List<AreaInvestimento> getAreePerAnno(Integer idAnno) {
		return arearep.findPerAnno(idAnno);
	}

}
