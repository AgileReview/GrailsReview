package org.surveyresults

import grails.test.*

class EvaluationControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_saving_an_evaluation_deep_saves_and_redirects_to_review_controller(){
		mockDomain(Question,[new Question(id:1,text:'foo'),new Question(id:2,text:'bar')])
		mockDomain(Review,[new Review(id:1)])
		mockDomain(Evaluation,[])
		mockDomain(Response,[])
		mockDomain(TeamMember,[new TeamMember(id:1,name:'foo')])
		mockDomain(Answer,[new Answer(id:1,text:'foo',value:1)])
		def controller = new EvaluationController()
		controller.params['review.id'] = 1
		controller.params['responses[0].question.id'] = "1"
		controller.params['responses[0].answer.id'] = "1"
		controller.params['responses[1].question.id'] = "2"
		controller.params['responses[1].answer.id'] = "1"
		controller.params['responder.id'] = "1"
		
		controller.save()
		def eval =  Evaluation.get(1)
		assertEquals eval.responses.size(),2
		def resp =  eval.responses.find {r -> r.question.id==2 }
		assertNotNull resp
		assertEquals resp.answer.id,1
		
		assertEquals "review",controller.redirectArgs.controller
		assertEquals "list",controller.redirectArgs.action
		
	}

	void test_create_provides_an_evaluation_from_evaluation_service_and_provides_all_answers(){
		
		//mock Answers
		def answers = [
				new Answer(id: 1,value: 1,text: 'good'),
				new Answer(id:2,value: 2,text: 'bad'),
				new Answer(id:3,value:3,text:'ugly')]
		mockDomain(Answer,answers)
		
		//create mocks for rewiewService
		def reviewID = 100
		def review = new Review(id:reviewID)
		mockDomain(Review,[review])
		def evaluation = new Evaluation()
		def evaluationServiceControl = mockFor(EvaluationService)
		def reviewParam
		evaluationServiceControl.demand.createBlankEvaluation(){r -> reviewParam=r; return evaluation}
		
		def controller = new EvaluationController()
		controller.params.reviewID = reviewID
		controller.evaluationService = evaluationServiceControl.createMock()
		def response = controller.create()['evaluationViewModel']
		//make sure response is the view model we're after (do we need a view model yet?)
		assertNotNull response
		assertTrue response instanceof EvaluationViewModel
		assertEquals evaluation, response.evaluation
		assertEquals reviewParam, review
		assertEquals response.answers.size(), 3
		assertEquals response.answers[2].text, 'ugly'
		
		
	}
}
