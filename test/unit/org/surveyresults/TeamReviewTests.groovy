package org.surveyresults

import grails.test.*

class TeamReviewTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_average_results_rolls_up_evaluation_results() {
		mockDomain(TeamReview,[])
		
		def tr = new TeamReview()
		def r1 = new Review()
		r1.averageScores = [3l:1,4l:2]
		def r2 = new Review()
		r2.averageScores = [3l:2,4l:4]
		tr.addToReviews(r1)
		tr.addToReviews(r2)
		def res = tr.averageScores
		
		assertEquals 1.5,res[3l]
		assertEquals 3,res[4l]
    }
	
	void test_average_results_by_role_only_includes_given_role(){
		mockDomain(TeamReview,[])
		
		def tr = new TeamReview()
		def rl1 = new Role(name:'find',id:1)
		def rl2 = new Role(name:'dontFind',id:2)
		
		def r1 = new Review(reviewee:new TeamMember(role:rl1))
		def r2 = new Review(reviewee:new TeamMember(role:rl1))
		def r3 = new Review(reviewee:new TeamMember(role:rl2))
		r1.averageScores = [3l:1,4l:2]
		r2.averageScores = [3l:2,4l:4]
		r3.averageScores = [3l:100,4l:400] //these should get thrown out
		tr.addToReviews(r1)
		tr.addToReviews(r2)
		tr.addToReviews(r3)
		def res = tr.getAverageScores(rl1)
		
		assertEquals 1.5,res[3l]
		assertEquals 3,res[4l]
	}
}
