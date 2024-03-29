package com.example.demo.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.FormRiconciliazione;
import com.example.demo.model.OrdineDiAcquistoDettaglio;
import com.example.demo.model.SottoCategoria;
import com.example.demo.service.OrdineAcquistoDettaglioService;
import com.example.demo.service.SottoCategoriaService;
import com.example.demo.util.UDate;

@Controller
@RequestMapping(value = "Budget")
public class BudgetController {

	@Autowired
	SottoCategoriaService sotser;

	@Autowired
	OrdineAcquistoDettaglioService detser;

	@Autowired
	SottoCategoria sotcat;

	@RequestMapping(value = "/Lista", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("Menu/Budget");
		return model;
	}

	@GetMapping(value = "/Riconciliazione")
	public ModelAndView riconciliazione() {
		ModelAndView model = new ModelAndView("Budget/Riconciliazione");
		model.addObject("elencoSottoCategorie", sotser.getAllSottoCategorie());
		model.addObject("oggettoForm", new FormRiconciliazione());
		return model;
	}

	@PostMapping(value = "/SaveRiconciliazione")
	public ModelAndView saveRiconciliazione(@Valid @ModelAttribute("oggettoForm") FormRiconciliazione form,
			HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		List<OrdineDiAcquistoDettaglio> dettagli = new ArrayList<OrdineDiAcquistoDettaglio>();
		try {
			dettagli = detser.getOrdiniPerSottoCategorieDate(UDate.inserisciStringa(form.getDatainizio()),
					UDate.inserisciStringa(form.getDatafine()));
//			for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
//				if (dettaglio.isRiconciliato())
//					dettagli.remove(dettaglio);
//			}
//			if (dettagli.size() != 0) {
				sotser.riconcilia(dettagli);
				model.addObject("messaggio", "Riconciliazione effettuata correttamente");
				for (OrdineDiAcquistoDettaglio dettaglio : dettagli) {
//					dettaglio.setRiconciliato(true);
					detser.saveOrUpdate(dettaglio);
				}
//			}
		} catch (ParseException e) {
			model.addObject("messaggio", "Riconciliazione non effettuata correttamente");
		}

		model.setViewName("Budget/Riconciliazione");
		return model;
	}

}
