package org.surveyresults

import org.apache.commons.collections.Factory
import org.apache.commons.collections.ListUtils
import org.hibernate.cache.ReadWriteCache.Item;

class Evaluation {

	
	TeamMember responder
	static hasMany =[responses:Response]
	static belongsTo=[review:Review]
	Comment comment
	static constraints = {
		comment(nullable:true)
		responses(validator: {
			if (it.findAll{resp -> resp.answer == null}.size() > 0) 
				return ['incomplete.evaluation']
		})


	}
	
	List responses = ListUtils.lazyList([],{new Response()} as Factory)
}
