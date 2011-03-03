package org.surveyresults

class Comment {

	String text
	static belongsTo=[review:Review]
    static constraints = {
    }
}
