package com.project.batacademy.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "activityCompletion")
public class ActivityCompletion {
	private Activity activity;
	private RegisteredCoursesId registeredCoursesId;
	private boolean courseCompleted;
	
	
	public ActivityCompletion() {
		
	}
	
	public ActivityCompletion(Activity activity,RegisteredCoursesId registeredCoursesId, boolean completed) {
		this.activity = activity;
		this.courseCompleted = completed;
		this.registeredCoursesId = registeredCoursesId;
	}
	
	/* Getters and Setters*/
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public boolean isCourseCompleted() {
		return courseCompleted;
	}
	public void setCourseCompleted(boolean courseCompleted) {
		this.courseCompleted = courseCompleted;
	}

	public RegisteredCoursesId getRegisteredCoursesId() {
		return registeredCoursesId;
	}

	public void setRegisteredCoursesId(RegisteredCoursesId registeredCoursesId) {
		this.registeredCoursesId = registeredCoursesId;
	}

	@Override
	public String toString() {
		return "ActivityCompletion [activity=" + activity + ", registeredCoursesId=" + registeredCoursesId
				+ ", courseCompleted=" + courseCompleted + "]";
	}

	

}
