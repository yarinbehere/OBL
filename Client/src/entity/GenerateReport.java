package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class GenerateReport implements Serializable{
	// activity report
	private LocalDate date;
	private int active;
	private int frozen;
	private int locked;
	private int books;
	private int lates;
	// borrow report
	private ArrayList<Integer> histogram;
	private double normalAvg;
	private double wantedAvg;
	private double normalMed;
	private double wantedMed;
	
	public GenerateReport(LocalDate date, int active, int frozen, int locked, int books, int lates) {
		this.date = date;
		this.active = active;
		this.frozen = frozen;
		this.locked = locked;
		this.books = books;
		this.lates = lates;
	}
	
	




	public GenerateReport(ArrayList<Integer> histogram, double normalAvg, double wantedAvg, double normalMed,
			double wantedMed) {
		super();
		this.histogram = histogram;
		this.normalAvg = normalAvg;
		this.wantedAvg = wantedAvg;
		this.normalMed = normalMed;
		this.wantedMed = wantedMed;
	}






	public ArrayList<Integer> getHistogram() {
		return histogram;
	}






	public void setHistogram(ArrayList<Integer> histogram) {
		this.histogram = histogram;
	}






	public double getNormalAvg() {
		return normalAvg;
	}







	public void setNormalAvg(double normalAvg) {
		this.normalAvg = normalAvg;
	}







	public double getWantedAvg() {
		return wantedAvg;
	}







	public void setWantedAvg(double wantedAvg) {
		this.wantedAvg = wantedAvg;
	}







	public double getNormalMed() {
		return normalMed;
	}







	public void setNormalMed(double normalMed) {
		this.normalMed = normalMed;
	}







	public double getWantedMed() {
		return wantedMed;
	}







	public void setWantedMed(double wantedMed) {
		this.wantedMed = wantedMed;
	}







	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getFrozen() {
		return frozen;
	}

	public void setFrozen(int frozen) {
		this.frozen = frozen;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getBooks() {
		return books;
	}

	public void setBooks(int books) {
		this.books = books;
	}

	public int getLates() {
		return lates;
	}

	public void setLates(int lates) {
		this.lates = lates;
	}


}
