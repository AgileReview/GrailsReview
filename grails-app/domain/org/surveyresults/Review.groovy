package org.surveyresults

class Review {

	
	TeamMember reviewee
	static belongsTo = [teamReview:TeamReview]
	static hasMany =[evaluations:Evaluation]
	
	boolean complete  = false
		
    static constraints = {
    }
}
