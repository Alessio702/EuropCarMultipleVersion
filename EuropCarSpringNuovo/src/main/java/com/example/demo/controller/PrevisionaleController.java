package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.AnnoContabile;
import com.example.demo.model.Area;
import com.example.demo.model.Fornitore;
import com.example.demo.model.Preventivo;
import com.example.demo.model.Previsione;
import com.example.demo.model.Venditore;
import com.example.demo.service.AreaService;
import com.example.demo.service.AziendaService;
import com.example.demo.service.PrevisioneService;
import com.example.demo.service.SottoCategoriaService;
import com.example.demo.service.VenditoreService;

@Controller
@RequestMapping(value = "/Previsionale")
public class PrevisionaleController {
	
	@Autowired
	VenditoreService venser;
	
	@Autowired
	PrevisioneService preser;
	
	@Autowired
	AreaService areser;
	
	@Autowired
	AziendaService aziser;
	
	@Autowired
	SottoCategoriaService sotser;
	
	
	@GetMapping(value = "/Cerca")
	public ModelAndView cercaPrevisionali(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		if (sessionObj.getAttribute("oggettoVenditorePrevisionale") != null) {
			Venditore ovenditore = (Venditore) sessionObj.getAttribute("oggettoVenditorePrevisionale");
			model.addObject("elencoPrevisionali", preser.getPrevisionaleByVend(ovenditore.getIdvenditore()));
		} else {
			sessionObj.setAttribute("oggettoVenditorePrevisionale", new Venditore());
		}
		model.addObject("oggettoVenditoreSelezione", new Venditore());
		model.addObject("elencoVenditori", venser.getAllVenditori());
		model.setViewName("Previsione/ListaPrevisionali");
		return model;
	}

	@PostMapping(value = "/ListaPrevisionali")
	public ModelAndView listaPreventivi(@ModelAttribute("oggettoVenditoreSelezione") Venditore ovenditore, HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoVenditoreSelezione", ovenditore);
		sessionObj.setAttribute("oggettoVenditorePrevisionale", ovenditore);
		model.addObject("elencoVenditori", venser.getAllVenditori());
		model.addObject("elencoPrevisionali", preser.getPrevisionaleByVend(ovenditore.getIdvenditore()));
		model.setViewName("Previsione/ListaPrevisionali");
		return model;
	}
	
	@GetMapping(value = "/AddPrevisionale")
	public ModelAndView addPrevisionale(HttpSession sessionObj) {
		ModelAndView model = new ModelAndView();
		Previsione oprevisione = new Previsione();
		AnnoContabile oannocontabile = (AnnoContabile) sessionObj.getAttribute("oggettoAnnoPermanente");
		oprevisione.setAnnodiriferimento(oannocontabile.getDescrizione().substring(0, 4));
		model.addObject("oggettoPrevisionale", oprevisione);
		model.addObject("elencoAree", areser.getAllAree());
		model.addObject("elencoAziende", aziser.getAllAziende());
		model.addObject("elencoVenditori", venser.getAllVenditori());
		model.setViewName("Previsione/AddEditPrevisionale");
		return model;
	}

	@GetMapping(value = "/EditPrevisionale/{id}")
	public ModelAndView editPreventivo(@PathVariable Integer id) {
		ModelAndView model = new ModelAndView();
		model.addObject("oggettoPreventivo", preser.getPrevisionaleById(id));
		model.addObject("elencoAree", areser.getAllAree());
		model.addObject("elencoAziende", aziser.getAllAziende());
		model.addObject("elencoVenditori", venser.getAllVenditori());
		model.setViewName("Previsione/AddEditPrevisionaleo");
		return model;
	}

	@PostMapping(value = "/SavePrevisionale")
	public ModelAndView savePrevisionale(@Valid @ModelAttribute("oggettoPrevisionale") Previsione oprevisionale,
			BindingResult bindingresult, HttpSession sessionObj, Model modelFornitore) {
		if (bindingresult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("Previsione/AddEditPrevisionale");
			return model;
		} else {
			preser.saveOrUpdate(oprevisionale);
			return new ModelAndView("redirect:/Preventivo/Cerca");
		}
	}

	@GetMapping(value = "/DeletePrevisionale/{id}")
	public ModelAndView deletePrevisionale(@PathVariable("id") Integer id) {
		preser.deletePrevisionale(id);
		return new ModelAndView("redirect:/Previsionale/Cerca");
	}

	
	
}
