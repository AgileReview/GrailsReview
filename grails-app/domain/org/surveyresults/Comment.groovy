package org.surveyresults

class Comment {

	String text
	static belongsTo=[evaluation:Evaluation]
    static constraints = {
    }
}
