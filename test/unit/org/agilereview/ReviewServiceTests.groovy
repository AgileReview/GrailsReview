package org.agilereview

import grails.test.*
import groovy.mock.interceptor.MockFor

class ReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_creating_a_review_creates_evaluations_for_each_team_member_except_reviewee() {
        mockDomain(Review, [])
        mockDomain(TeamMember, [])
        def team = [new TeamMember(name: 'fred'),
                new TeamMember(name: 'gary')
                , new TeamMember(name: 'jerry')]
        def evCtrl = mockFor(EvaluationService)
        def evParams = []
        evCtrl.demand.createBlankEvaluation(2) {x -> evParams << x; new Evaluation()}

        def reviewService = new ReviewService()
        reviewService.evaluationService = evCtrl.createMock()
        def review = reviewService.createBlankReview(team.find {x->x.name=='fred'},team)
        assertEquals review.evaluations.size(),2
        assertEquals evParams, team.findAll {x->x.name!='fred'}

        evCtrl.verify()
    }

    void test_completing_an_evaluation_when_other_evaluations_are_complete_completes_review_and_saves() {
		mockDomain(Review,[])
		def trCtrl = mockFor(TeamReviewService)
		def trParam
		def tr = new TeamReview()
		trCtrl.demand.reviewCompleted(){r->trParam = r}
		def saved = false

		def review = new Review(complete:false,teamReview:tr,reviewee: new TeamMember())

		review.addToEvaluations(new Evaluation(complete:true))
		review.addToEvaluations(new Evaluation(complete:true))
		def rs = new ReviewService()
		rs.teamReviewService = trCtrl.createMock()
		rs.evaluationCompleted review
		assertTrue review.complete
		assertNotNull review.id
		trCtrl.verify()
		assertSame trParam,tr
    }
	
	void test_completing_an_evaluation_when_other_evaluations_are_not_complete_does_not_complete_review_and_does_not_save() {
		mockDomain(Review,[])
		def saved = false
		Review.metaClass.save = {-> saved=true}
		def review = new Review(complete:false)
		review.addToEvaluations(new Evaluation(complete:false))
		review.addToEvaluations(new Evaluation(complete:true))
		def rs = new ReviewService()
		rs.evaluationCompleted review
		assertFalse review.complete
		assertFalse saved
	}
	

	

}
