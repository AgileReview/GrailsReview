package org.surveyresults

class Review {

	
	TeamMember reviewee
	static hasMany =[evaluations:Evaluation]
	String quarter
		
    static constraints = {
    }
}
