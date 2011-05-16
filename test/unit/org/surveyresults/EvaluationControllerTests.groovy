package org.surveyresults

import grails.test.*
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

class EvaluationControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_creating_when_user_is_not_logged_in_redirects_to_login_page(){
		def controller = new EvaluationController()
		controller.teamMemberService = mock_current_user(null)
		controller.update()
		assertEquals controller.redirectArgs.action,'login'
		assertEquals controller.redirectArgs.controller,'teamMember'
	}
	
	void test_saving_when_user_is_not_logged_in_redirects_to_login_page(){
		
		def controller = new EvaluationController()
		controller.teamMemberService = mock_current_user(null)
		controller.save()
		assertEquals controller.redirectArgs.action,'login'
		assertEquals controller.redirectArgs.controller,'teamMember'
	
	}
	
	void test_saving_an_evaluation_deep_saves_and_redirects_to_review_controller(){
		def teamMember = new TeamMember(id:1,name:'foo')
		mockDomain(Question,[new Question(id:1,text:'foo'),new Question(id:2,text:'bar')])
		mockDomain(Review,[new Review(id:1)])
		mockDomain(Evaluation,[])
		mockDomain(Response,[])
		mockDomain(TeamMember,[teamMember])
		mockDomain(Answer,[new Answer(id:1,text:'foo',value:1)])
		def controller = new EvaluationController()
		controller.params['review.id'] = 1
		controller.params['responses[0].question.id'] = "1"
		controller.params['responses[0].answer.id'] = "1"
		controller.params['responses[1].question.id'] = "2"
		controller.params['responses[1].answer.id'] = "1"
		
		controller.teamMemberService = mock_current_user(teamMember)
		controller.save()
		
		def eval =  Evaluation.get(1)
		assertEquals eval.responses.size(),2
		def resp =  eval.responses.find {r -> r.question.id==2 }
		assertNotNull resp
		assertEquals resp.answer.id,1
		assertEquals eval.responder.name,'foo'
		assertEquals "teamMember",controller.redirectArgs.controller
		assertEquals "index",controller.redirectArgs.action
		
	}

	void test_saving_an_invalid_evalution_redirects_to_update(){
		mockDomain(Answer,[])
		def mock = new MockFor(Evaluation)
		mock.demand.setResponder{}
		mock.demand.validate{ false}
		
		def controller = new EvaluationController()
		controller.teamMemberService = mock_current_user(new TeamMember(id:1,name:'foo'))
		mock.use{
			controller.save()
		}
		assertEquals "update",controller.renderArgs.view
		
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
		

		def user = new TeamMember(name:'Patrick Escarcega')

		def controller = new EvaluationController()
		controller.params.evaluationID = evaluationID

		controller.teamMemberService = mock_current_user(user )
		def response = controller.update()['evaluationViewModel']
		//make sure response is the view model we're after (do we need a view model yet?)
		assertNotNull response
		assertTrue response instanceof EvaluationViewModel
		assertEquals evaluation, response.evaluationInstance

		assertEquals response.answers.size(), 3
		assertEquals response.answers[2].text, 'ugly'
		
	}
	
	def mock_current_user(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController.createMock()
	}
	
}
