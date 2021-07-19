package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable<CountryAndNumber>{
	
	private Country country;
	private Integer number;
	
	public CountryAndNumber(Country country, Integer number) {
		super();
		this.country = country;
		this.number = number;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public int compareTo(CountryAndNumber o) {
		// ordine DECRESCENTE per numero mettiamo IL MENO
		return - this.getNumber().compareTo(o.number);
	}

	@Override
	public String toString() {
		return country.getStateAbb() + " - " + country.getStateName() + " = " + this.number;
	}
	
	

}
