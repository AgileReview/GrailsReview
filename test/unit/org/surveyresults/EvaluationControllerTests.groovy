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
	
	void test_saving_an_evaluation_calls_service_and_redirects_to_review_controller(){
		def teamMember = new TeamMember(id:1,name:'foo')
		mockDomain(TeamMember,[teamMember])

		def evaluation = new Evaluation(responder:new TeamMember(),id:1)
		mockDomain(Evaluation,[evaluation])

		def evCtrl = mockFor(EvaluationService)
		def evParam
		evCtrl.demand.complete(){e->evParam=e}
		def tmCtrl = mock_current_user(teamMember)
		def controller = new EvaluationController()
		controller.params.id = 1

		controller.evaluationService = evCtrl.createMock()
		controller.teamMemberService = tmCtrl.createMock()
		controller.save()
		
		assertSame evParam,evaluation
		
		//assuming things went well we should be directed back to the teamMember controller
		assertEquals "teamMember",controller.redirectArgs.controller
		assertEquals "index",controller.redirectArgs.action

		evCtrl.verify()
		tmCtrl.verify()

	}

	void test_saving_an_invalid_evalution_redirects_to_update(){
		mockDomain(Answer,[])
		def evaluation = new Evaluation(id:1)
		mockDomain(Evaluation,[evaluation])

		def tmCtrl = mock_current_user(new TeamMember(id:1,name:'foo'))
		def evCtrl = mockFor(EvaluationService)
		evCtrl.demand.complete(){e->false}
		def controller = new EvaluationController()
		controller.teamMemberService = tmCtrl.createMock()
		controller.evaluationService = evCtrl.createMock()
		controller.params.id = 1
		controller.save()

		assertEquals "update",controller.renderArgs.view
		tmCtrl.verify()
		evCtrl.verify()
		
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
