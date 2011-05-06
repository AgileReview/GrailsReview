package org.surveyresults

class Review {

	
	Person reviewee
	static hasMany =[evaluations:Evaluation,comments:Comment]
	String quarter
	
    static constraints = {
    }
}
