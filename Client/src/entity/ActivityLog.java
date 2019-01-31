package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class ActivityLog implements Serializable
{
	private String activity;
	private String date;
	
	public ActivityLog(String date, String activity) {
		this.activity = activity;
		this.date=date;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
