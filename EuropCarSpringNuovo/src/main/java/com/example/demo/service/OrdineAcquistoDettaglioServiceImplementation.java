package com.example.demo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrdineDiAcquistoDettaglio;
import com.example.demo.model.SottoCategoria;
import com.example.demo.repository.OrdineAcquistoDettaglioRepository;
import com.example.demo.util.UDate;

@Service
@Transactional
public class OrdineAcquistoDettaglioServiceImplementation implements OrdineAcquistoDettaglioService {

	@Autowired
	OrdineAcquistoDettaglioRepository orddetrep;

	@Override
	public List<OrdineDiAcquistoDettaglio> getAllFatturePassiveDettaglio() {
		return (List<OrdineDiAcquistoDettaglio>) orddetrep.findAll();
	}

	@Override
	public OrdineDiAcquistoDettaglio getOrdineAcquistoDettaglioById(Integer idOrdineAcquisto) {
		return orddetrep.findById(idOrdineAcquisto).get();
	}

	@Override
	public OrdineDiAcquistoDettaglio saveOrUpdate(OrdineDiAcquistoDettaglio oOrdineAcquisto) {
		return orddetrep.save(oOrdineAcquisto);
	}

	@Override
	public void deleteOrdineAcquistoDettaglio(Integer idOrdineAcquisto) {
		orddetrep.deleteById(idOrdineAcquisto);
	}

	@Override
	public List<OrdineDiAcquistoDettaglio> getOrdiniPerSottoCategoria(int idsottocategoria, Integer idAnno) {
		return orddetrep.ordiniPerSottoCategorie(idsottocategoria, idAnno);
	}

	@Override
	public List<OrdineDiAcquistoDettaglio> getOrdiniPerProgetto(Integer idprogetto, Integer idAnno) {
		return orddetrep.ordiniPerProgetto(idprogetto, idAnno);
	}

	@Override
	public List<OrdineDiAcquistoDettaglio> getOrdiniPerSottoCategorieDate(String estraiData, String estraiData2) throws ParseException {
	
			return orddetrep.dettagliPerSottoCategoriaDate2(UDate.estraiData(estraiData), UDate.estraiData(estraiData2));
	
		
	}


//	@Override
//	public List<OrdineDiAcquistoDettaglio> filterDates(List<OrdineDiAcquistoDettaglio> dettagli, String datainizio,
//			String datafine) throws ParseException {
//		List<OrdineDiAcquistoDettaglio> listaDettagli = new ArrayList<>();
//		UDate udate = new UDate();
//		if (dettagli.size() != 0) {
//			for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
//				if (udate.estraiData(dettaglio.getOordineacquisto().getData()).getTime() >= udate.estraiData(datainizio)
//						.getTime()
//						&& udate.estraiData(dettaglio.getOordineacquisto().getData()).getTime() <= udate
//								.estraiData(datafine).getTime()) {
//					listaDettagli.add(dettaglio);
//				}
//			}
//		}
//		return listaDettagli;
//	}

}
