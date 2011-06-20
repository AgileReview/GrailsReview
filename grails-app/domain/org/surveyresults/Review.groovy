package org.surveyresults

class Review {

	
	TeamMember reviewee
	def averageScores = null
	static belongsTo = [teamReview:TeamReview]
	static hasMany =[evaluations:Evaluation]
	static transients = ['averageScores']
	
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
	
	boolean complete  = false
		
    static constraints = {
    }
}
