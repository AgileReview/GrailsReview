package org.agilereview

import org.apache.commons.collections.Factory
import org.apache.commons.collections.ListUtils

class Evaluation {

	String comments
	TeamMember responder
	def results = null
	boolean resultsGathered = false
	static transients = ['results']
	boolean complete = false
	static hasMany =[responses:Response]
	static belongsTo=[review:Review]
	
	def getResults(){
		if(!results){
			def res = [:]
			responses.each {r->res[r.question.id] = r.answer.value}
			results = res
		}
		results
	}
	
	
	
	static constraints = {
		comments(nullable:true,maxSize: 1000)
		responses(validator: {val, obj ->
			if ((val.findAll{resp -> resp.answer == null}.size() > 0) && obj.complete) 
				return ['incomplete.evaluation']
		})
	}
	
	List responses = ListUtils.lazyList([],{new Response()} as Factory)
}
