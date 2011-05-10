package org.surveyresults

import org.apache.commons.collections.Factory
import org.apache.commons.collections.ListUtils

class Evaluation {

	
	TeamMember responder
	static hasMany =[responses:Response]
	static belongsTo=[review:Review]
	Comment comment
	static constraints = {
		comment(nullable:true)
	}
	
	List responses = ListUtils.lazyList([],{new Response()} as Factory)
}
