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
}
