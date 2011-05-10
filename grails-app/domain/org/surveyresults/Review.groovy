package org.surveyresults

class Review {

	
	TeamMember reviewee
	static hasMany =[evaluations:Evaluation,comments:Comment]
	String quarter
	
    static constraints = {
    }
}
