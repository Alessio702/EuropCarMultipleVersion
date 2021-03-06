package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrdineDiAcquistoDettaglio;
import com.example.demo.model.SottoCategoria;
import com.example.demo.repository.SottoCategoriaRepository;

@Service
@Transactional
public class SottoCategoriaServiceImplementation implements SottoCategoriaService {

	@Autowired
	SottoCategoriaRepository sotrep;

	@Override
	public List<SottoCategoria> getAllSottoCategorie() {
		return (List<SottoCategoria>) sotrep.findAll();
	}

	@Override
	public SottoCategoria getSottoCategoriaById(Integer idSottoCategoria) {
		return sotrep.findById(idSottoCategoria).get();
	}

	@Override
	public SottoCategoria saveOrUpdate(SottoCategoria oSottoCategoria) {
		return sotrep.save(oSottoCategoria);

	}

	@Override
	public void deleteSottoCategoria(Integer idSottoCategoria) {
		sotrep.deleteById(idSottoCategoria);
	}

	@Override
	public List<SottoCategoria> getSottoCategoriePerAnno(int idannocontabile) {
		return sotrep.findSottoPerAnno(idannocontabile);
	}


	@Override
	public void riconcilia(List<OrdineDiAcquistoDettaglio> dettagli) {
		List<SottoCategoria> sottocategorie = new ArrayList<>();
		HashMap<SottoCategoria, Integer> sessionObj = new HashMap<>();
		for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
			if (sessionObj.get(dettaglio.getOspesainvestimento().getOsottocategoria()) != null) {
				sessionObj.put(dettaglio.getOspesainvestimento().getOsottocategoria(),
						 (int) (sessionObj.get(dettaglio.getOspesainvestimento().getOsottocategoria()) + dettaglio.getImporto()));
			} else {
				sessionObj.put(dettaglio.getOspesainvestimento().getOsottocategoria(), Math.round(dettaglio.getImporto()));
				sottocategorie.add(dettaglio.getOspesainvestimento().getOsottocategoria());
			} 
		}
		for(SottoCategoria sottocategoria : sottocategorie) {
			sottocategoria.setBudgetspeso(sessionObj.get(sottocategoria));
			sotrep.save(sottocategoria);
		}
		
	}

}
