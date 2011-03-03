package org.surveyresults

class Review {

	
	Person person
	static hasMany =[responses:Response,comments:Comment]

	
    static constraints = {
    }
}
