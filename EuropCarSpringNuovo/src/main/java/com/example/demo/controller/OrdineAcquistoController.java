package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.DettaglioNotFoundException;
import com.example.demo.model.AnnoContabile;
import com.example.demo.model.Fornitore;
import com.example.demo.model.OrdineAcquisto;
import com.example.demo.model.OrdineDiAcquistoDettaglio;
import com.example.demo.model.Progetto;
import com.example.demo.model.SottoCategoria;
import com.example.demo.service.FornitoreService;
import com.example.demo.service.OrdineAcquistoDettaglioService;
import com.example.demo.service.OrdineAcquistoService;
import com.example.demo.service.ProgettoService;
import com.example.demo.service.SottoCategoriaService;
import com.example.demo.service.SpesaInvestimentoService;

@Controller
@RequestMapping(value = "/OrdineAcquisto")
public class OrdineAcquistoController {

	@Autowired
	OrdineAcquistoService ordser;

	@Autowired
	OrdineAcquistoDettaglioService orddetser;

	@Autowired
	FornitoreService forser;

	@Autowired
	ProgettoService proser;

	@Autowired
	SpesaInvestimentoService spesaser;

	@Autowired
	SottoCategoriaService sotser;

	private int count;
	private List<Integer> chiavi;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Integer> getChiavi() {
		return chiavi;
	}

	public void setChiavi(List<Integer> chiavi) {
		this.chiavi = chiavi;
	}

	public Integer idAnno(HttpSession sessionObj) {
		AnnoContabile oanno = (AnnoContabile) sessionObj.getAttribute("oggettoAnnoPermanente");
		return oanno.getIdannocontabile();
	}

	@GetMapping(value = "/Menu")
	public ModelAndView lista() {
		ModelAndView model = new ModelAndView("OrdineAcquisto/MenuOrdini");
		return model;
	}

	@GetMapping(value = "/DeleteOrdine/{id}")
	public ModelAndView deleteOrdine(@PathVariable("id") Integer id) {
		ordser.deleteOrdineAcquisto(id);
		return new ModelAndView("redirect:/OrdineAcquisto/Cerca");
	}

	@GetMapping(value = "/Cerca")
	public ModelAndView cercaOrdine(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView("OrdineAcquisto/ListaOrdini");
		sessionObj.setAttribute("oggettoFornitoreOrdine", new Fornitore());
		model.addObject("oggettoFornitoreOrdine", new Fornitore());
		
		model.addObject("elencoFornitori", forser.getAllFornitori());
		sessionObj.setAttribute("oggettoOrdineTemporaneo", null);
		sessionObj.setAttribute("oggettoDettagliTemporanei", null);
		sessionObj.setAttribute("contatore", null);
		sessionObj.setAttribute("chiavi", null);
		return model;
	}

	@PostMapping(value = "/ListaOrdini")
	public ModelAndView listaOrdini(@ModelAttribute("oggettoFornitoreOrdine") Fornitore ofornitore,
			HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		sessionObj.setAttribute("oggettoFornitoreOrdine", ofornitore);
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.addObject("oggettoFornitoreOrdine", ofornitore);
		model.addObject("elencoOrdini", ordser.getOrdiniPerFornitore(ofornitore.getIdfornitore(), idAnno(sessionObj)));
		model.setViewName("OrdineAcquisto/ListaOrdini");
		return model;
	}

	@GetMapping(value = "/RedirectOrdine")
	public ModelAndView redirectOrdini(HttpSession sessionObj, ModelAndView model) {
		//////////////////////
		Fornitore ofornitore = (Fornitore) sessionObj.getAttribute("oggettoFornitoreOrdine");
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.addObject("oggettoFornitoreOrdine", ofornitore);
		model.addObject("elencoOrdini", ordser.getOrdiniPerFornitore(ofornitore.getIdfornitore(), idAnno(sessionObj)));
		model.setViewName("OrdineAcquisto/ListaOrdini");
		return model;
	}

	/////////////// Sezione Add

	@GetMapping(value = "/AddOrdine")
	public ModelAndView addOrdine(HttpSession sessionObj) {
		this.count = 0;
		sessionObj.setAttribute("contatore", this.count);
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoOrdineTemporaneo", new OrdineAcquisto());
		model.setViewName("OrdineAcquisto/AddOrdine");
		return model;
	}

	@PostMapping(value = "/AddDettagli")
	public ModelAndView addDettagli(@Valid @ModelAttribute("oggettoOrdineTemporaneo") OrdineAcquisto oOrdinetemporaneo,
			BindingResult bindingresult, HttpSession sessionObj) {
		if (bindingresult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("OrdineAcquisto/AddOrdine");
			model.addObject("elencoFornitori", forser.getAllFornitori());
			return model;
		} else {
			ModelAndView model = new ModelAndView();
			sessionObj.setAttribute("oggettoOrdineTemporaneo", oOrdinetemporaneo);
			model.addObject("oggettoDettagliTemporanei", new OrdineDiAcquistoDettaglio());
			model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
			model.addObject("elencoProgetti", proser.getAllProgetti());
			model.setViewName("OrdineAcquisto/AddDettagli");
			return model;
		}
	}

	@GetMapping(value = "/AddDettagliSuccessivi")
	public ModelAndView addDettagliSuccessivi(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoDettagliTemporanei", new OrdineDiAcquistoDettaglio());
		model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
		model.addObject("elencoProgetti", proser.getAllProgetti());
		model.setViewName("OrdineAcquisto/AddDettagli");
		return model;
	}

	@PostMapping(value = "/SaveDettagliListaNuova")
	public ModelAndView saveDettagli(
			@Valid @ModelAttribute("oggettoDettagliTemporanei") OrdineDiAcquistoDettaglio odettaglitemporanei,
			BindingResult bindingresult, HttpSession sessionObj) {
		if (bindingresult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
			model.addObject("elencoProgetti", proser.getAllProgetti());
			model.setViewName("OrdineAcquisto/AddDettagli");
			return model;
		} else {
			ModelAndView model = new ModelAndView();
			this.count = (int) sessionObj.getAttribute("contatore");
			odettaglitemporanei.setIdentifier(this.count + 1);
			sessionObj.setAttribute("contatore", this.count);
			OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
			oordine.getDettagli().add(odettaglitemporanei);
			sessionObj.setAttribute("oggettoOrdineTemporaneo", oordine);
			sessionObj.setAttribute("oggettoDettagliTemporanei", oordine.getDettagli());
			model.addObject("oggettoOrdineTemporaneo", oordine);

			model.addObject("elencoDettagli", oordine.getDettagli());
			model.setViewName("OrdineAcquisto/AddOrdineConDettagli");
			return model;
		}
	}

	@GetMapping(value = "/Annulla")
	public ModelAndView annulla(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		Fornitore ofornitore = (Fornitore) sessionObj.getAttribute("oggettoFornitoreOrdine");
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.addObject("oggettoFornitoreOrdine", ofornitore);
		model.addObject("elencoOrdini", ordser.getOrdiniPerFornitore(ofornitore.getIdfornitore(), idAnno(sessionObj)));
		model.setViewName("OrdineAcquisto/ListaOrdini");
		return model;
	}

	@GetMapping(value = "/AnnullaDettagli")
	public ModelAndView annullaDettagli(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
		model.addObject("oggettoOrdineTemporaneo", oordine);
		model.addObject("elencoDettagli", oordine.getDettagli());

		model.setViewName("OrdineAcquisto/AddOrdineConDettagli");
		return model;
	}

	@GetMapping(value = "/RimuoviDettagliTemporanei/{identifier}")
	public ModelAndView rimuoviDettagliTemporanei(@PathVariable Integer identifier, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
		for (int i = 0; i < oordine.getDettagli().size(); i++) {
			if (oordine.getDettagli().get(i).getIdentifier() == identifier) {
				oordine.getDettagli().remove(i);
			}
		}
		sessionObj.setAttribute("oggettoOrdineTemporaneo", oordine);
		sessionObj.setAttribute("oggettoDettagliTemporanei", oordine.getDettagli());
		model.addObject("oggettoOrdineTemporaneo", oordine);
		model.addObject("elencoDettagli", oordine.getDettagli());

		model.setViewName("OrdineAcquisto/AddOrdineConDettagli");
		return model;
	}

	@PostMapping(value = "/SaveOrdine")
	public ModelAndView saveOrdine(@Valid @ModelAttribute("oggettoOrdineTemporaneo") OrdineAcquisto oordine,
			BindingResult bindingresult, HttpSession sessionObj, Model modelFornitore) {
		List<OrdineDiAcquistoDettaglio> dettagli = new ArrayList<OrdineDiAcquistoDettaglio>();
		if (sessionObj.getAttribute("oggettoDettagliTemporanei") != null) {
			dettagli = (List<OrdineDiAcquistoDettaglio>) sessionObj.getAttribute("oggettoDettagliTemporanei");
		}
		ModelAndView model = new ModelAndView();
		if (bindingresult.hasErrors() || dettagli.size() == 0) {
			OrdineAcquisto oOrdine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
			model.addObject("elencoDettagli", oOrdine.getDettagli());
			model.addObject("elencoFornitori", forser.getAllFornitori());
			if (dettagli.size() == 0) {
				model.addObject("messaggio",
						"I dettagli devono essere inseriti obbligatoriamente per poter continuare");
			}
			model.setViewName("OrdineAcquisto/AddOrdineConDettagli");

			return model;
		} else {
			oordine.setImporto(0);
			for (int i = 0; i < dettagli.size(); i++) {
				oordine.getDettagli().add(dettagli.get(i));
				oordine.setImporto(oordine.getImporto() + dettagli.get(i).getImporto());
			}
			Fornitore ofornitore = (Fornitore) sessionObj.getAttribute("oggettoFornitoreOrdine");
			oordine.setOfornitore(ofornitore);
			ordser.saveOrUpdate(oordine);
			for (int i = 0; i < oordine.getDettagli().size(); i++) {
				oordine.getDettagli().get(i).setOordineacquisto(oordine);
				orddetser.saveOrUpdate(oordine.getDettagli().get(i));
			}
			sessionObj.removeAttribute("oggettoOrdineTemporaneo");
			sessionObj.removeAttribute("oggettoDettagliTemporanei");
			sessionObj.removeAttribute("contatore");

			this.redirectOrdini(sessionObj, model);
			return model;
		}
	}
	
	@ExceptionHandler(DettaglioNotFoundException.class)
	public ModelAndView handleHerror(HttpServletRequest request, DettaglioNotFoundException exception) {
		ModelAndView model = new ModelAndView();
		model.addObject("codice", exception.getOrdineacquisto());
		model.addObject("exception", exception);
		model.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
		model.setViewName("Exception/noDettagli");
		return model;
	}

	///////////////////////////// Sezione Edit

	@GetMapping(value = "/EditOrdine/{id}")
	public ModelAndView editOrdine(@PathVariable Integer id, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		OrdineAcquisto oordine = ordser.getOrdineAcquistoById(id);
		sessionObj.setAttribute("oggettoOrdineTemporaneo", oordine);
		sessionObj.setAttribute("oggettoFornitore", oordine.getOfornitore());
		;
		model.addObject("oggettoOrdineTemporaneo", oordine);
		model.addObject("elencoDettagli", oordine.getDettagli());
		sessionObj.setAttribute("oggettoDettagliTemporanei", oordine.getDettagli());


		sessionObj.setAttribute("contatore", this.count = 0);
		model.setViewName("OrdineAcquisto/EditOrdine");
		return model;
	}

	@GetMapping(value = "/EditDettaglio/{id}")
	public ModelAndView editDettagli(@PathVariable Integer id, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoDettaglioTemporaneo", orddetser.getOrdineAcquistoDettaglioById(id));
		model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
		model.addObject("elencoProgetti", proser.getAllProgetti());
		model.setViewName("OrdineAcquisto/EditDettaglio");
		return model;
	}

	@GetMapping(value = "/AnnullaModifica")
	public ModelAndView annullaModifica(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		Fornitore ofornitore = (Fornitore) sessionObj.getAttribute("oggettoFornitoreOrdine");

		model.addObject("oggettoFornitoreOrdine", ofornitore);
		model.addObject("elencoOrdini", ordser.getOrdiniPerFornitore(ofornitore.getIdfornitore(), idAnno(sessionObj)));
		model.setViewName("OrdineAcquisto/ListaOrdini");
		return model;
	}

	@PostMapping(value = "/SaveDettagliModificati")
	public ModelAndView saveDettagliModificati(
			@Valid @ModelAttribute("oggettoDettaglioTemporaneo") OrdineDiAcquistoDettaglio odettagliotemporaneo,
			BindingResult bindingresult, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		if (bindingresult.hasErrors()) {
			model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
			model.addObject("elencoProgetti", proser.getAllProgetti());
			model.setViewName("OrdineAcquisto/EditDettaglio");
			return model;
		} else {
			OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
			for (int i = 0; i < oordine.getDettagli().size(); i++) {
				if (oordine.getDettagli().get(i).getIdordinediacquistodettaglio() == odettagliotemporaneo
						.getIdordinediacquistodettaglio()) {
					oordine.getDettagli().get(i).setImporto(odettagliotemporaneo.getImporto());
					oordine.getDettagli().get(i).setOprogetto(odettagliotemporaneo.getOprogetto());
					oordine.getDettagli().get(i).setOspesainvestimento(odettagliotemporaneo.getOspesainvestimento());
					oordine.getDettagli().get(i).setQuantita(odettagliotemporaneo.getQuantita());
				}
			}
			sessionObj.setAttribute("oggettoOrdineTemporaneo", oordine);
			sessionObj.setAttribute("oggettoDettagliTemporanei", oordine.getDettagli());
			model.addObject("oggettoOrdineTemporaneo", oordine);
			model.addObject("elencoDettagli", oordine.getDettagli());


			model.setViewName("OrdineAcquisto/EditOrdine");
			return model;
		}
	}

	@PostMapping(value = "/SaveEditOrdine")
	public ModelAndView saveEditOrdine(@Valid @ModelAttribute("oggettoOrdineTemporaneo") OrdineAcquisto oordine,
			BindingResult bindingresult, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		List<OrdineDiAcquistoDettaglio> dettagli = new ArrayList<OrdineDiAcquistoDettaglio>();
		if (sessionObj.getAttribute("oggettoDettagliTemporanei") != null) {
			dettagli = (List<OrdineDiAcquistoDettaglio>) sessionObj.getAttribute("oggettoDettagliTemporanei");
		}
		if (bindingresult.hasErrors() || dettagli.size() == 0) {
			OrdineAcquisto oOrdine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
			if (dettagli.size() == 0) {
				model.addObject("messaggio",
						"I dettagli devono essere inseriti obbligatoriamente per poter continuare");
			}
			model.addObject("elencoDettagli", oOrdine.getDettagli());
			model.addObject("elencoFornitori", forser.getAllFornitori());
			model.setViewName("OrdineAcquisto/EditOrdine");
			return model;
		} else {
			oordine.getDettagli().clear();
			oordine.setImporto(0);
			Fornitore ofornitore = (Fornitore) sessionObj.getAttribute("oggettoFornitoreOrdine");
			oordine.setOfornitore(ofornitore);
			for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
				dettaglio.setOordineacquisto(oordine);
				oordine.getDettagli().add(dettaglio);
				oordine.setImporto(oordine.getImporto() + dettaglio.getImporto());
			}
			ordser.saveOrUpdate(oordine);
//			for (int i = 0; i < oordine.getDettagli().size(); i++) {
//				orddetser.saveOrUpdate(dettagli.get(i));
//			}
			sessionObj.removeAttribute("oggettoOrdineTemporaneo");
			sessionObj.removeAttribute("oggettoDettagliTemporanei");
			sessionObj.removeAttribute("contatore");
			this.redirectOrdini(sessionObj, model);
			return model;
		}
	}

	@GetMapping(value = "/AnnullaDettagliModifica")
	public ModelAndView annullaDettagliModifica(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
		model.addObject("oggettoOrdineTemporaneo", oordine);
		model.addObject("elencoDettagli", oordine.getDettagli());
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.setViewName("OrdineAcquisto/EditOrdine");
		return model;
	}

	@GetMapping(value = "/AddDettagliModifica/")
	public ModelAndView addDettagliModifica() {
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoDettaglioTemporaneo", new OrdineDiAcquistoDettaglio());
		model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
		model.addObject("elencoProgetti", proser.getAllProgetti());
		model.setViewName("OrdineAcquisto/AddDettaglioModifica");
		return model;
	}

	@PostMapping(value = "/SaveDettagliAggiunti")
	public ModelAndView saveDettagliAggiunti(
			@Valid @ModelAttribute("oggettoDettaglioTemporaneo") OrdineDiAcquistoDettaglio odettagliotemporaneo,
			BindingResult bindingresult, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		if (bindingresult.hasErrors()) {
			model.addObject("elencoSpese", spesaser.getAllSpeseInvestimento());
			model.addObject("elencoProgetti", proser.getAllProgetti());
			model.setViewName("OrdineAcquisto/EditDettaglio");
			return model;
		} else {
			OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
//			List<OrdineDiAcquistoDettaglio> dettagli = (List<OrdineDiAcquistoDettaglio>) sessionObj.getAttribute("oggettoDettagliTemporanei");
//			for(OrdineDiAcquistoDettaglio dettaglio : dettagli) {
//				oordine.getDettagli().add(dettaglio);
//			}
			this.count = (int) sessionObj.getAttribute("contatore") + 1;
			odettagliotemporaneo.setIdentifier(this.count);
			sessionObj.setAttribute("contatore", this.count);
			oordine.getDettagli().add(odettagliotemporaneo);
			sessionObj.setAttribute("oggettoOrdineTemporaneo", oordine);
			sessionObj.setAttribute("oggettoDettagliTemporanei", oordine.getDettagli());
			model.addObject("oggettoOrdineTemporaneo", oordine);
			model.addObject("elencoDettagli", oordine.getDettagli());
			model.addObject("elencoFornitori", forser.getAllFornitori());
			model.setViewName("OrdineAcquisto/EditOrdine");
			return model;
		}
	}

//	
	@GetMapping(value = "/RimuoviDettagli/{id}/{identifier}")
	public ModelAndView rimuoviDettagli(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "identifier") Integer identifier, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
//		if (sessionObj.getAttribute("chiavi") != null) {
//			for (int i = 0; i < ((List<Integer>) sessionObj.getAttribute("chiavi")).size(); i++) {
//				this.chiavi.add(((List<Integer>) sessionObj.getAttribute("chiavi")).get(i));
//			}
//			this.chiavi.add(id);
//			sessionObj.setAttribute("chiavi", this.chiavi);
//		} else {
//			sessionObj.setAttribute("chiavi", id);
//		}
		OrdineAcquisto oordine = (OrdineAcquisto) sessionObj.getAttribute("oggettoOrdineTemporaneo");
		List<OrdineDiAcquistoDettaglio> dettagli = (List<OrdineDiAcquistoDettaglio>) sessionObj
				.getAttribute("oggettoDettagliTemporanei");
		boolean check = false;
		if (id != null) {
			for (int i = 0; i < dettagli.size() && !check; i++) {
				if (id == dettagli.get(i).getIdordinediacquistodettaglio()) {
					oordine.getDettagli().remove(dettagli.get(i));
					check = true;
				}
			}
		} else {
			for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
				if (identifier == dettaglio.getIdentifier())
					oordine.getDettagli().remove(dettaglio);
			}
		}
		model.addObject("oggettoOrdineTemporaneo", oordine);
		model.addObject("elencoDettagli", oordine.getDettagli());
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.setViewName("OrdineAcquisto/EditOrdine");
		return model;
	}

	///////////////////////////// Sezione Ricerca

	@GetMapping(value = "/Ricerca")
	public ModelAndView ricerca() {
		ModelAndView model = new ModelAndView("OrdineAcquisto/RicercaOrdini");
		return model;
	}

	@GetMapping(value = "/Fornitore")
	public ModelAndView sceltaFornitore() {
		ModelAndView model = new ModelAndView();
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.addObject("oggettoFornitore", new Fornitore());
		model.setViewName("OrdineAcquisto/ElencoPerFornitore");
		return model;
	}

	@PostMapping(value = "CercaPerFornitore")
	public ModelAndView ricercaPerFornitore(@ModelAttribute Fornitore ofornitore, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		List<OrdineAcquisto> elenco = ordser.getOrdiniPerFornitore(ofornitore.getIdfornitore(), idAnno(sessionObj));
		AnnoContabile oanno = (AnnoContabile) sessionObj.getAttribute("oggettoAnnoPermanente");
		boolean trovato;
		for (int i = 0; i < elenco.size(); i++) {
			List<OrdineDiAcquistoDettaglio> elencoDettagli = elenco.get(i).getDettagli();
			trovato = false;
			for (int j = 0; j < elencoDettagli.size() && !trovato; j++) {
				if (elencoDettagli.get(j).getOspesainvestimento().getOsottocategoria().getOarea().getOannocontabile()
						.getIdannocontabile() != oanno.getIdannocontabile()) {
					elenco.remove(i);
					trovato = true;
				}
			}
		}
		model.addObject("elencoOrdiniAcquisto", elenco);
		model.setViewName("OrdineAcquisto/ElencoPerFornitore");
		model.addObject("elencoFornitori", forser.getAllFornitori());
		model.addObject("oggettoFornitore", ofornitore);
		return model;
	}

	@GetMapping(value = "/Progetto")
	public ModelAndView sceltaProgetto() {
		ModelAndView model = new ModelAndView();
		model.addObject("elencoProgetti", proser.getAllProgetti());
		model.addObject("oggettoProgetto", new Progetto());
		model.setViewName("OrdineAcquisto/ElencoPerProgetto");
		return model;
	}

	@PostMapping(value = "/CercaPerProgetto")
	public ModelAndView ricercaPerProgetto(@ModelAttribute Progetto oprogetto, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		model.addObject("elencoProgetti", proser.getAllProgetti());
		model.addObject("oggettoProgetto", oprogetto);
		AnnoContabile oanno = (AnnoContabile) sessionObj.getAttribute("oggettoAnnoPermanente");
		List<OrdineDiAcquistoDettaglio> listadettagli = orddetser.getOrdiniPerProgetto(oprogetto.getIdprogetto(),
				oanno.getIdannocontabile());
		List<OrdineAcquisto> listaordini = new ArrayList<OrdineAcquisto>();
		for (int i = 0; i < listadettagli.size(); i++) {
			listaordini.add(listadettagli.get(i).getOordineacquisto());
		}
		boolean trovato;
		if (listaordini.size() != 0) {
			for (int i = listaordini.size() - 1; i > 0; i--) {
				trovato = false;
				for (int j = listaordini.size() - 2; j >= 0 && !trovato; j--) {
					if (listaordini.get(i).getIdordineacquisto() == listaordini.get(j).getIdordineacquisto()) {
						listaordini.remove(j);
						trovato = true;
					}
				}
			}
		}
		model.addObject("elencoOrdiniAcquisto", listaordini);
		model.setViewName("OrdineAcquisto/ElencoPerProgetto");
		return model;
	}

	@GetMapping(value = "/SottoCategoria")
	public ModelAndView sceltaSottoCategoria() {
		ModelAndView model = new ModelAndView();
		model.addObject("elencoSottoCategorie", sotser.getAllSottoCategorie());
		model.addObject("oggettoSottoCategoria", new SottoCategoria());
		model.setViewName("OrdineAcquisto/ElencoPerSottoCategoria");
		return model;
	}

	@PostMapping(value = "/CercaPerSottoCategoria")
	public ModelAndView ricercaPerSottoCategoria(@ModelAttribute SottoCategoria osottocategoria,
			HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		model.addObject("elencoSottoCategorie", sotser.getAllSottoCategorie());
		model.addObject("oggettoSottoCategoria", osottocategoria);
		AnnoContabile oanno = (AnnoContabile) sessionObj.getAttribute("oggettoAnnoPermanente");
		List<OrdineDiAcquistoDettaglio> listadettagli = orddetser
				.getOrdiniPerSottoCategoria(osottocategoria.getIdsottocategoria(), oanno.getIdannocontabile());
		List<OrdineAcquisto> listaordini = new ArrayList<OrdineAcquisto>();
		for (int i = 0; i < listadettagli.size(); i++) {
			listaordini.add(listadettagli.get(i).getOordineacquisto());
		}
		boolean trovato;
		if (listaordini.size() != 0) {
			for (int i = listaordini.size() - 1; i > 0; i--) {
				trovato = false;
				for (int j = listaordini.size() - 2; j >= 0 && !trovato; j--) {
					if (listaordini.get(i).getIdordineacquisto() == listaordini.get(j).getIdordineacquisto()) {
						listaordini.remove(j);
						trovato = true;
					}
				}
			}
		}
		model.addObject("elencoOrdiniAcquisto", listaordini);
		model.setViewName("OrdineAcquisto/ElencoPerSottoCategoria");
		return model;
	}

}
