package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
	
	// supponiamo di avere un solo tipo di evento
	
	
	private int t;               // tempo
	private Country country;     // stato destinazione
	private int n;               // num persone arrivate al tempo t nello stato country
	
	public Evento(int t, Country country, int n) {
		super();
		this.t = t;
		this.country = country;
		this.n = n;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int compareTo(Evento o) {
		// non è integer non si puo usare compareTo
		return (this.t - o.t);
	}


	
}
