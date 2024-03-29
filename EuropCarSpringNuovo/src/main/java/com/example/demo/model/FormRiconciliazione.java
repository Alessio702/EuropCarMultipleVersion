package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class FormRiconciliazione {
	
	
	List<SottoCategoria> osottocategoria;
	
	@NotNull(message="il campo non può essere vuoto")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
	Date datainizio;
	
	@NotNull(message="il campo non può essere vuoto")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
	Date datafine;

	



	public Date getDatainizio() {
		return datainizio;
	}

	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}

	public Date getDatafine() {
		return datafine;
	}

	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}


	
	

}
