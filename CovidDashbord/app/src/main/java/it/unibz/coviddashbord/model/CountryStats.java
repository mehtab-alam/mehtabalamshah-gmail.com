package it.unibz.coviddashbord.model;

public class CountryStats {
	String country;
	String totalCases;
	String newCases;
	String totalDeaths;
	String newDeaths;
	String totalRecovered;
	String activeCases;
	String seriousCritical;
	String totalCasesPerMillion;
	String deathsPerMillion;
	String firstCase;
	
	
	public String getCountry() {
		return country;
	}


	public String getTotalCases() {
		return totalCases;
	}


	public void setTotalCases(String totalCases) {
		this.totalCases = totalCases;
	}


	public String getNewCases() {
		return newCases;
	}


	public void setNewCases(String newCases) {
		this.newCases = newCases;
	}


	public String getTotalDeaths() {
		return totalDeaths;
	}


	public void setTotalDeaths(String totalDeaths) {
		this.totalDeaths = totalDeaths;
	}


	public String getNewDeaths() {
		return newDeaths;
	}


	public void setNewDeaths(String newDeaths) {
		this.newDeaths = newDeaths;
	}


	public String getTotalRecovered() {
		return totalRecovered;
	}


	public void setTotalRecovered(String totalRecovered) {
		this.totalRecovered = totalRecovered;
	}


	public String getActiveCases() {
		return activeCases;
	}


	public void setActiveCases(String activeCases) {
		this.activeCases = activeCases;
	}


	public String getSeriousCritical() {
		return seriousCritical;
	}


	public void setSeriousCritical(String seriousCritical) {
		this.seriousCritical = seriousCritical;
	}


	public String getTotalCasesPerMillion() {
		return totalCasesPerMillion;
	}


	public void setTotalCasesPerMillion(String totalCasesPerMillion) {
		this.totalCasesPerMillion = totalCasesPerMillion;
	}


	public String getDeathsPerMillion() {
		return deathsPerMillion;
	}


	public void setDeathsPerMillion(String deathsPerMillion) {
		this.deathsPerMillion = deathsPerMillion;
	}


	public String getFirstCase() {
		return firstCase;
	}


	public void setFirstCase(String firstCase) {
		this.firstCase = firstCase;
	}


	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
