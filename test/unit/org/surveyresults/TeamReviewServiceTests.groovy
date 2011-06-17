package org.surveyresults

import grails.test.*

class TeamReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_completing_a_review_when_other_reviews_are_complete_completes_teamreview_and_saves() {
		mockDomain(TeamReview,[])
		def saved = false
		TeamReview.metaClass.save = {-> saved=true}
		def tr = new TeamReview(complete:false)
		tr.addToReviews(new Review(complete:true))
		tr.addToReviews(new Review(complete:true))
		def rs = new TeamReviewService()
		rs.reviewCompleted tr
		assertTrue tr.complete
		assertTrue saved
    }
	
	void test_completing_an_evaluation_when_other_evaluations_are_not_complete_does_not_complete_review_and_does_not_save() {
		mockDomain(TeamReview,[])
		def saved = false
		TeamReview.metaClass.save = {-> saved=true}
		def tr = new TeamReview(complete:false)
		tr.addToReviews(new Review(complete:false))
		tr.addToReviews(new Review(complete:true))
		def rs = new TeamReviewService()
		rs.reviewCompleted tr
		assertFalse tr.complete
		assertFalse saved
	}
}
