package org.surveyresults

class Response {
	
	static belongsTo = [review:Review]
	Question question
	Answer answer
	
    static constraints = {
    }
}
