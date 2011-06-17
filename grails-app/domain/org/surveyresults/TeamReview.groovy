package org.surveyresults

class TeamReview {
	
	static hasMany = [reviews:Review]
	boolean complete = false
	String name
	
	
    static constraints = {
    }
}
