package org.surveyresults

import grails.test.*

class ReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_completing_an_evaluation_when_other_evaluations_are_complete_completes_review_and_saves() {
		mockDomain(Review,[])
		def saved = false
		Review.metaClass.save = {-> saved=true}
		def review = new Review(complete:false)
		review.addToEvaluations(new Evaluation(complete:true))
		review.addToEvaluations(new Evaluation(complete:true))
		def rs = new ReviewService()
		rs.evalCompleted review
		assertTrue review.complete
		assertTrue saved
    }
	
	void test_completing_an_evaluation_when_other_evaluations_are_not_complete_does_not_complete_review_and_does_not_save() {
		mockDomain(Review,[])
		def saved = false
		Review.metaClass.save = {-> saved=true}
		def review = new Review(complete:false)
		review.addToEvaluations(new Evaluation(complete:false))
		review.addToEvaluations(new Evaluation(complete:true))
		def rs = new ReviewService()
		rs.evalCompleted review
		assertFalse review.complete
		assertFalse saved
	}
}
