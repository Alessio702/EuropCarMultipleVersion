package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORDINEDIACQUISTODETTAGLIO")
public class OrdineDiAcquistoDettaglio {

	public static final String PROPERTY_IDOrdineAcquisto = "oordineacquisto";

	@Column(name = "idordinediacquistodettaglio")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idordinediacquistodettaglio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idordineacquisto")
	private OrdineAcquisto oordineacquisto;

	@ManyToOne
	@JoinColumn(name = "idspesainvestimento")
	private SpesaInvestimento ospesainvestimento;

	@ManyToOne
	@JoinColumn(name = "idprogetto")
	@NotNull(message = "il campo non può essere nullo")
	private Progetto oprogetto;

	@Column(name = "importo")
	@NotNull(message = "il campo non può essere vuoto")
	private Float importo;

	@Column(name = "quantita")
	@NotNull(message = "il campo non può essere vuoto")
	private int quantita;
	
	@Column(name = "riconciliaro")
	private boolean riconciliato;
	
	@Transient
	private int identifier;
	
	
	
	
	
//	public OrdineDiAcquistoDettaglio() {
//		this.riconciliato = false;
//	}

//	public boolean isRiconciliato() {
//		return riconciliato;
//	}
//
//
//
//	public void setRiconciliato(boolean riconciliato) {
//		this.riconciliato = riconciliato;
//	}


	public int getIdentifier() {
		return identifier;
	}
	
	




	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public int getIdordinediacquistodettaglio() {
		return idordinediacquistodettaglio;
	}

	public void setIdordinediacquistodettaglio(int idordinediacquistodettaglio) {
		this.idordinediacquistodettaglio = idordinediacquistodettaglio;
	}

	public OrdineAcquisto getOordineacquisto() {
		return oordineacquisto;
	}

	public void setOordineacquisto(OrdineAcquisto oordineacquisto) {
		this.oordineacquisto = oordineacquisto;
	}

	public SpesaInvestimento getOspesainvestimento() {
		return ospesainvestimento;
	}

	public void setOspesainvestimento(SpesaInvestimento ospesainvestimento) {
		this.ospesainvestimento = ospesainvestimento;
	}

	public Progetto getOprogetto() {
		return oprogetto;
	}

	public void setOprogetto(Progetto oprogetto) {
		this.oprogetto = oprogetto;
	}

	public Float getImporto() {
		return importo;
	}

	public void setImporto(Float importo) {
		this.importo = importo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

}