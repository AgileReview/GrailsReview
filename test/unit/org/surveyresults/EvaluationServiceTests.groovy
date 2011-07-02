package org.surveyresults

import grails.test.*
import groovy.mock.interceptor.MockFor

class EvaluationServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_complete_sets_user_sets_complete_saves_and_calls_review_service(){
		def review = new Review()
		def evCtrl = new MockFor(Evaluation)
		evCtrl.demand.setComplete{b->null}
		evCtrl.demand.validate{true}

		evCtrl.demand.save{true}
		evCtrl.demand.getReview{review}
		def rCtrl = mockFor(ReviewService)
		
		def rParam
		rCtrl.demand.evaluationCompleted(){r->rParam=r}
		def es = new EvaluationService()
		es.reviewService = rCtrl.createMock()
		evCtrl.use{
			assertTrue es.complete(new Evaluation())
		}
		assertSame rParam,review
		rCtrl.verify()
	}
	
	void test_complete_when_eval_is_invalid_sets_returns_false(){

		def evCtrl = new MockFor(Evaluation)
        evCtrl.demand.setComplete{b->null}
		evCtrl.demand.validate{false}

		def es = new EvaluationService()
		boolean res
		evCtrl.use{
			res= es.complete(new Evaluation())
		}
		assertFalse res
	}

    void test_when_creating_a_blank_evaluation_all_questions_are_available_in_order() {
		//generate exactly two questions
		def reviews = []
		def responses = []
		mockDomain(Review,[])
		mockDomain(Response,[])
		mockDomain(Evaluation,[])
		mockDomain(Question,[new Question(id: 2,text:'who what'),new Question(id:1,text: 'what where')])
		def service = new EvaluationService()
		
		
		def evaluation = service.createBlankEvaluation(new TeamMember(name:'Patrick Escarcega'))
		assert(evaluation.class ==Evaluation)
		
		assertEquals evaluation.responses.size(), 2
		assertNotNull evaluation.responses.find {r -> r.question.text == 'what where'}
		//ensure that the evaluation isn't saved to the database
		assertEquals 0,Evaluation.list().size()
    }


}
