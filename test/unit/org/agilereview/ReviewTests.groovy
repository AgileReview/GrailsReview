package org.agilereview

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
		
		assertEquals 1.5,res[3l],0
		assertEquals 3,res[4l],0
    }

	void test_comments_rolls_up_evaluation_comments() {
		mockDomain(Review,[])
		
		def r = new Review()
		def e1 = new Evaluation(comments:'foo')
		
		def e2 = new Evaluation(comments:'bar')
		def e3 = new Evaluation(comments:null)
		r.addToEvaluations(e1)
		r.addToEvaluations(e2)
		r.addToEvaluations(e3)
	
		def x = ['bar','foo']
		assertEquals x,r.comments.sort()
	}
	
	void test_maximum_results_rolls_up_evaluation_results() {
		mockDomain(Review,[])
		
		def r = new Review()
		def e1 = new Evaluation()
		e1.results = [3l:3,4l:1]
		def e2 = new Evaluation()
		e2.results = [3l:2,4l:4]
		r.addToEvaluations(e1)
		r.addToEvaluations(e2)
		def res = r.maximumScores
		
		assertEquals 3,res[3l]
		assertEquals 4,res[4l]
	}
	void test_minimum_results_rolls_up_evaluation_results() {
		mockDomain(Review,[])
		
		def r = new Review()
		def e1 = new Evaluation()
		e1.results = [3l:3,4l:1]
		def e2 = new Evaluation()
		e2.results = [3l:2,4l:4]
		r.addToEvaluations(e1)
		r.addToEvaluations(e2)
		def res = r.minimumScores
		
		assertEquals 2,res[3l]
		assertEquals 1,res[4l]
	}
}
