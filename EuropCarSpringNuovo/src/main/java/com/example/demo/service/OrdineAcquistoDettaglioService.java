package com.example.demo.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.example.demo.model.OrdineDiAcquistoDettaglio;
import com.example.demo.model.SottoCategoria;

public interface OrdineAcquistoDettaglioService {
	
	public List<OrdineDiAcquistoDettaglio> getAllFatturePassiveDettaglio();
	
	public OrdineDiAcquistoDettaglio getOrdineAcquistoDettaglioById(Integer idOrdineAcquisto);
	
	public OrdineDiAcquistoDettaglio saveOrUpdate(OrdineDiAcquistoDettaglio oOrdineAcquisto);
	
	public void deleteOrdineAcquistoDettaglio(Integer idOrdineAcquisto);

	List<OrdineDiAcquistoDettaglio> getOrdiniPerSottoCategoria(int idsottocategoria, Integer idAnno);
	
	public List<OrdineDiAcquistoDettaglio> getOrdiniPerProgetto(Integer idprogetto, Integer idAnno);

	public List<OrdineDiAcquistoDettaglio> getOrdiniPerSottoCategorieDate(String dateInizio, String dataFine) throws ParseException;

//	public List<OrdineDiAcquistoDettaglio> filterDates(List<OrdineDiAcquistoDettaglio> dettagli, String datainizio, String datafine) throws ParseException;

}
