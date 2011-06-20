package org.surveyresults

class Review {

	
	TeamMember reviewee
	boolean complete  = false
	def averageScores = null
	def maximumScores = null
	def minimumScores = null
	static belongsTo = [teamReview:TeamReview]
	static hasMany =[evaluations:Evaluation]
	static transients = ['averageScores','minimumScores','maximumScores']
	
	def getAverageScores(){
		if(!averageScores){
			def res = [:]
			evaluations.each {e->e.results.each {question,score->res[question] = 0 }}
			evaluations.each {e->e.results.each {question,score->res[question]+=score }}
			res.each { question,total->res[question] = res[question]/evaluations.size()}
			averageScores = res
		}
		averageScores
	}
	
	def getMinimumScores(){
		if(!minimumScores){
			def res = [:]
			def all = [:]
			evaluations.each {e->e.results.each {question,score->all[question]  = [] }}
			evaluations.each { e->e.results.each {q,a-> all[q].push(a)}}
			all.each {q,a->res[q] = a.min() }
			minimumScores = res
		}
		minimumScores
	}
	
	def getMaximumScores(){
		if(!maximumScores){
			def res = [:]
			def all = [:]
			evaluations.each {e->e.results.each {question,score->all[question]  = [] }}
			evaluations.each { e->e.results.each {q,a-> all[q].push(a)}}
			all.each {q,a->res[q] = a.max() }
			maximumScores = res
		}
		maximumScores
	}
	

	
	
		
    static constraints = {
    }
}
