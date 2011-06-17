package org.surveyresults

import grails.test.*
import groovy.mock.interceptor.MockFor

class ReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	

    void test_completing_an_evaluation_when_other_evaluations_are_complete_completes_review_and_saves() {
		mockDomain(Review,[])
		def trCtrl = mockFor(TeamReviewService)
		def trParam
		def tr = new TeamReview()
		trCtrl.demand.reviewCompleted(){r->trParam = r}
		def saved = false
		Review.metaClass.save = {-> saved=true}
		def review = new Review(complete:false,teamReview:tr)
		review.addToEvaluations(new Evaluation(complete:true))
		review.addToEvaluations(new Evaluation(complete:true))
		def rs = new ReviewService()
		rs.teamReviewService = trCtrl.createMock()
		rs.evaluationCompleted review
		assertTrue review.complete
		assertTrue saved
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
	
	void test_example_review_has_results_calculated_correctly(){
		rs = new ReviewService()
		def rr = rs.calculateScoresForReview(getExampleReview)
		assertEquals 2,rr.size()
		//get ReviewResult for q1
		def q1 = rr.find {r -> r.question.text == 'q1'}
		assertNotNull q1
		assertEquals 2.33,q1.yourScore
		//assertEquals 1,q1.teamAverage
		//assertEquals 1,q1.roleAverage
		//assertEquals 1,q1.minAnswer
		//assertEquals 1,q1.maxAnswer
	}
	
	void getExampleReview(){
		mockDomain(Review,[])
		mockDomain(Evaluation,[])
		review = new Review(complete:true)
		def p1 = new TeamMember(role:new Role(name:'qa'),name:'p1')
		def p2 = new TeamMember(role:new Role(name:'qa'),name:'p2')
		def p3 = new TeamMember(role:new Role(name:'dev'),name:'p3')
		
		def q1 = new Question(text:'q1')
		def q2 = new Question(text:'q2')
		def a1 = new Answer(value:2)
		def a2 = new Answer(value:3)
		
		def p1eval = new Evaluation(responder:p1)
		p1eval.addToResponses(new Response(question:q1,answer:a1))//2
		p1eval.addToResponses(new Response(question:q2,answer:a1))//2
		
		def p2eval = new Evaluation(responder:p2)
		p2eval.addToResponses(new Response(question:q1,answer:a1))//2
		p2eval.addToResponses(new Response(question:q2,answer:a2))//3

		def p3eval = new Evaluation(responder:p3)
		p3eval.addToResponses(new Response(question:q1,answer:a2))//3
		p3eval.addToResponses(new Response(question:q2,answer:a2))//3
		
		review.addToEvaluations(p1eval)
		review.addToEvaluations(p2eval)
		review.addToEvaluations(p3eval)
		review
		
	}
}
