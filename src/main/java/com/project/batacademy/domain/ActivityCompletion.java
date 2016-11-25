package com.project.batacademy.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "activityCompletion")
public class ActivityCompletion {
	private Activity activity;
	private boolean courseCompleted;
	
	
	public ActivityCompletion() {
		
	}
	
	public ActivityCompletion(Activity activity, boolean completed) {
		this.activity = activity;
		this.courseCompleted = completed;
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

	@Override
	public String toString() {
		return "StudentActivity [activity=" + activity + ", courseCompleted=" + courseCompleted + "]";
	}

	

}
