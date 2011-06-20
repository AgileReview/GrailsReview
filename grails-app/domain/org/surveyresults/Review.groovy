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
			evaluations.each {e->e.results.each {question,score->res.get(question,0);res[question]+=score }}
			res.each { question,total->res[question] = res[question]/evaluations.size()}
			averageScores = res
		}
		averageScores
	}
	
	def getMinimumScores(){
		if(!minimumScores){
			minimumScores = findMinOrMax(true)
		}
		minimumScores
	}
	
	def getMaximumScores(){
		if(!maximumScores){
			maximumScores = findMinOrMax(false)
		}
		maximumScores
	}
	
	def findMinOrMax(def maxormin){
		def res = [:]
		def all = [:]
		evaluations.each { e->e.results.each {q,a-> all.get(q,[]) <<a}}
		all.each {q,a->res[q] = maxormin ? a.min():a.max() }
		minimumScores = res
	}
	

	
	
		
    static constraints = {
    }
}
