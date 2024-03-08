package datamodel;

import java.time.LocalDateTime;
import java.util.Date;


public class Schedule {
	
	private int scheduleId;
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getTourEvent() {
		return tourEvent;
	}
	public void setTourEvent(String tourEvent) {
		this.tourEvent = tourEvent;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public LocalDateTime getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(LocalDateTime scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	private String tourEvent;
	private Date scheduleDate;
	private LocalDateTime scheduleTime;
	

}
