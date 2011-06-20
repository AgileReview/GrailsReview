package org.surveyresults

import grails.test.*

class ReviewTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_average_results_rolls_up_evaluation_results() {
		mockDomain(Review,[])
		
		def r = new Review()
		def e1 = new Evaluation()
		e1.results = [3l:1,4l:2]
		def e2 = new Evaluation()
		e2.results = [3l:2,4l:4]
		r.addToEvaluations(e1)
		r.addToEvaluations(e2)
		def res = r.averageScores
		
		assertEquals 1.5,res[3l]
		assertEquals 3,res[4l]
    }
}
