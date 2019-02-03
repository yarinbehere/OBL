package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class GenerateReport implements Serializable{
	private LocalDate date;
	private int active;
	private int frozen;
	private int locked;
	private int books;
	private int lates;
	
	public GenerateReport(LocalDate date, int active, int frozen, int locked, int books, int lates) {
		this.date = date;
		this.active = active;
		this.frozen = frozen;
		this.locked = locked;
		this.books = books;
		this.lates = lates;
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
