package org.surveyresults

import grails.test.*
import groovy.mock.interceptor.MockFor


class EvaluationControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_creating_when_user_is_not_logged_in_redirects_to_login_page(){
		def controller = new EvaluationController()
		def tmCtrl = mock_current_user(null)
		controller.teamMemberService = tmCtrl.createMock()
		controller.update()
		assertEquals controller.redirectArgs.action,'login'
		assertEquals controller.redirectArgs.controller,'teamMember'
		tmCtrl.verify()
	}
	
	void test_saving_when_user_is_not_logged_in_redirects_to_login_page(){
		
		def controller = new EvaluationController()
		def tmCtrl = mock_current_user(null)
		controller.teamMemberService = tmCtrl.createMock()
		controller.save()
		assertEquals controller.redirectArgs.action,'login'
		assertEquals controller.redirectArgs.controller,'teamMember'
		tmCtrl.verify()
	
	}
	
	void test_saving_an_evaluation_deep_saves_completes_and_redirects_to_review_controller(){
		def teamMember = new TeamMember(id:1,name:'foo')
		mockDomain(Question,[new Question(id:1,text:'foo'),new Question(id:2,text:'bar')])
		mockDomain(Answer,[new Answer(id:2,text:'foo',value:1)])
		mockDomain(Review,[])
		mockDomain(Evaluation,[])
		mockDomain(Response,[])
		mockDomain(TeamMember,[teamMember])
		def review = new Review(id:1,quarter:'x',reviewee:new TeamMember())
		def evaluation = new Evaluation(responder:new TeamMember())
		def res =  new Response(question: Question.get(1))
		
		review.addToEvaluations(evaluation)
		review.save(flush:true)
		
		evaluation.review = review
		
		evaluation.save(failOnError:true)
		evaluation.addToResponses(res)
		evaluation.save(flush:true)
		res.save(flush:true)
		def rsCtrl = mockFor(ReviewService)
		rsCtrl.demand.evaluationCompleted(){}
		def tmCtrl = mock_current_user(teamMember)
		def controller = new EvaluationController()
		
		controller.params['id'] = evaluation.id
		controller.params['responses[0].answer.id'] = "2"
		controller.params['complete'] = 'true'
		controller.reviewService = rsCtrl.createMock()
		controller.teamMemberService = tmCtrl.createMock()
		controller.save()
		//assuming things went well we should be directed back to the teamMember controller
		assertEquals "teamMember",controller.redirectArgs.controller
		assertEquals "index",controller.redirectArgs.action
		
		def eval =  Evaluation.get(1)

		assertEquals eval.responses.size(),1
		assertNotNull eval.responses.find {r -> r.answer.id==2 }
		assertTrue eval.complete
		rsCtrl.verify()
		tmCtrl.verify()

	}

	void test_saving_an_invalid_evalution_redirects_to_update(){
		mockDomain(Answer,[])
		def mock = new MockFor(Evaluation)
		mock.demand.get{ new Evaluation()}
		mock.demand.setProperties{}
		mock.demand.setResponder{}
		mock.demand.validate{ false}
		def tmCtrl = mock_current_user(new TeamMember(id:1,name:'foo'))
		def controller = new EvaluationController()
		controller.teamMemberService = tmCtrl.createMock()
		mock.use{
			controller.save()
		}
		assertEquals "update",controller.renderArgs.view
		tmCtrl.verify()
		
	}

	void test_update_gets_evaluation_and_provides_all_answers(){
		
		//mock Answers
		def answers = [
				new Answer(id: 1,value: 1,text: 'good'),
				new Answer(id:2,value: 2,text: 'bad'),
				new Answer(id:3,value:3,text:'ugly')]
		mockDomain(Answer,answers)
		
		//create mocks for rewiewService
		def evaluationID = 100

		def evaluation = new Evaluation(id:evaluationID)
		mockDomain(Evaluation,[evaluation])
		

		def tmCtrl = mock_current_user( new TeamMember(name:'Patrick Escarcega'))

		def controller = new EvaluationController()
		controller.params.evaluationID = evaluationID

		controller.teamMemberService = tmCtrl.createMock()
		def response = controller.update()['evaluationViewModel']
		//make sure response is the view model we're after (do we need a view model yet?)
		assertNotNull response
		assertTrue response instanceof EvaluationViewModel
		assertEquals evaluation, response.evaluationInstance

		assertEquals response.answers.size(), 3
		assertEquals response.answers[2].text, 'ugly'
		tmCtrl.verify()
		
	}
	

	
	def mock_current_user(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController
	}
	
}
