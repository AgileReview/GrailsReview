package org.agilereview

class Response {
	
	static belongsTo = [evaluation:Evaluation]
	Question question
	Answer answer
	
	
    static constraints = {
		answer(nullable:true)
    }
}
