package main;

import java.util.List;

public class JsonConfig {
	   private List<String> number;
	    private List<String> possessives;
	    private List<String> cases;
	    private List<String> derivationSuffixes;
	    private List<String> tenses;

	    // Constructors, getters, and setters

	    public List<String> getNumber() {
	        return number;
	    }

	    public void setNumber(List<String> number) {
	        this.number = number;
	    }

	    public List<String> getPossessives() {
	        return possessives;
	    }

	    public void setPossessives(List<String> possessives) {
	        this.possessives = possessives;
	    }

	    public List<String> getCases() {
	        return cases;
	    }

	    public void setCases(List<String> cases) {
	        this.cases = cases;
	    }

	    public List<String> getDerivationSuffixes() {
	        return derivationSuffixes;
	    }

	    public void setDerivationSuffixes(List<String> derivationSuffixes) {
	        this.derivationSuffixes = derivationSuffixes;
	    }

	    public List<String> getTenses() {
	        return tenses;
	    }

	    public void setTenses(List<String> tenses) {
	        this.tenses = tenses;
	    }

	    @Override
	    public String toString() {
	        return "MyJsonData{" +
	                "number=" + number +
	                ", possessives=" + possessives +
	                ", cases=" + cases +
	                ", derivationSuffixes=" + derivationSuffixes +
	                ", tenses=" + tenses +
	                '}';
	    }

}
