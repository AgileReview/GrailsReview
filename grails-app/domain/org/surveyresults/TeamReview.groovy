package org.surveyresults

class TeamReview {
	
	static hasMany = [reviews:Review]
	static transients = ['averageScores']
	def averageScores = null
	boolean complete = false
	String name
	
	def getAverageScores(){
		if(!averageScores){
			def res = [:]
			reviews.each {r->r.averageScores.each {question,score->res[question] = 0 }}
			reviews.each {r->r.averageScores.each {question,score->res[question]+=score }}
			res.each { question,total->res[question] = res[question]/reviews.size()}
			averageScores = res
		}
		averageScores
	}
	
	
    static constraints = {
    }
}
