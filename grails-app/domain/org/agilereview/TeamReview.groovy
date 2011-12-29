package org.agilereview

class TeamReview {
	
	static hasMany = [reviews:Review]
	static transients = ['averageScores']
	def averageScores = [:]
	boolean complete = false
	String name
	
	def getAverageScores(role=null){
		def roleid = role? role.id:-1
		if(!averageScores[roleid]){
			def res = [:]
			def rvs = role?reviews.findAll { r->r.reviewee.role.id==role.id}:reviews
			rvs.each {r->r.averageScores.each {question,score->res.get(question,0);res[question]+=score }}
			res.each { question,total->res[question] = res[question]/rvs.size()}
			averageScores[roleid] = res
		}
		averageScores[roleid]
	}
	
	
    static constraints = {
    }
}
