package it.unibz.coviddashbord.model;

public class Dashboard {
	String coronaCases;
	String deaths;
	String recovered;
	String mild;
	String critical;
	String currentlyInfected;
	String outCome;
	String discharged;
	String lastUpdated;
	int mildPercentage;
	int seriousPercentage;
	int recoveredPercentage;
	int deathPercentage;

	public int getMildPercentage() { return mildPercentage; }
	public int getSeriousPercentage() { return seriousPercentage; }
	public int getRecoveredPercentage() { return recoveredPercentage; }
	public int getDeathPercentage() { return deathPercentage; }
	public String getLastUpdated() { return lastUpdated; }
	public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
	public String getDeaths() {
		return deaths;
	}
	public void setDeaths(String deaths) {
		this.deaths = deaths;
	}
	public String getCoronaCases() {
		return coronaCases;
	}
	public void setCoronaCases(String coronaCases) {
		this.coronaCases = coronaCases;
	}
	public String getRecovered() {
		return recovered;
	}
	public void setRecovered(String recovered) {
		this.recovered = recovered;
	}
	public String getMild() {
		return mild;
	}
	public void setMild(String mild) {
		this.mild = mild;
	}
	public String getCritical() {
		return critical;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public String getCurrentlyInfected() {
		return currentlyInfected;
	}
	public void setCurrentlyInfected(String currentlyInfected) { this.currentlyInfected = currentlyInfected; }
	public String getOutCome() {
		return outCome;
	}
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}
	public String getDischarged() {
		return discharged;
	}
	public void setDischarged(String discharged) {
		this.discharged = discharged;
	}
	
	public void setPercentages(){
		int currentlyInfectedInt = Integer.parseInt(currentlyInfected.replaceAll(",", ""));
		int mildInt = Integer.parseInt(mild.replaceAll(",", ""));
		int criticalInt = Integer.parseInt(critical.replaceAll(",", ""));
		int outcomeInt = Integer.parseInt(outCome.replaceAll(",", ""));
		int recoveredInt = Integer.parseInt(recovered.replaceAll(",", ""));
		int deathsInt = Integer.parseInt(deaths.replaceAll(",", ""));

		seriousPercentage = (int) Math.floor((criticalInt * 100)/currentlyInfectedInt);
		mildPercentage = 100 - seriousPercentage;
		deathPercentage = (int) Math.floor((deathsInt * 100)/outcomeInt);
		recoveredPercentage = 100 - deathPercentage;

	}
	
}
