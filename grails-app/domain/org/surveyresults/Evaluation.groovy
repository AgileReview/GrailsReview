package org.surveyresults

import org.apache.commons.collections.ListUtils

class Evaluation {

	
	//Person responder
	//static hasMany =[responses:Response]
	//static belongsTo=[review:Review]
	//Comment comment
	List x = new ListUtils.lazyList()
	static constraints = {
		//comment(nullable:true)
	}
	
	//List responses = new ListUtils.lazyList()//new ArrayList(),{new Response()} as Factory)
}
