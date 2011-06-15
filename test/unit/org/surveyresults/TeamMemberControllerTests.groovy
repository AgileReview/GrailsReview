package org.surveyresults

import grails.test.*

class TeamMemberControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_doLogin_redirects_to_review_controller_when_login_successful() {
		mockDomain(TeamMember,[new TeamMember(email:'me',password:'you',name:'x',role:new Role())])
		def session = [ : ]
		TeamMemberController.metaClass.getSession = { -> session }

		def controller = new TeamMemberController()
		controller.params.email = 'me'
		controller.params.password = 'you'
		controller.doLogin()

		assertEquals 'me',controller.session.teamMember.email
		assertEquals 'review',controller.redirectArgs.controller
		assertEquals 'list',controller.redirectArgs.action
    }
	
	void test_doLogin_redirects_to_login_controller_when_login_wrong() {
		mockDomain(TeamMember,[new TeamMember(email:'me',password:'them',name:'x',role:new Role())])
		def session = [ : ]
		TeamMemberController.metaClass.getSession = { -> session }

		def controller = new TeamMemberController()
		controller.params.email = 'me'
		controller.params.password = 'you'
		controller.doLogin()

		assertNull controller.session.teamMember

		assertEquals 'login',controller.redirectArgs.action
	}
	
	void test_index_returns_a_list_of_evaluations_to_complete_and_completed_reviews(){
		def evaluations = [new Evaluation(),new Evaluation()]
		def currentUser = new TeamMember()
		def otherUser = new TeamMember()
		def completeReview = new Review(reviewee:currentUser,complete:true)
		def incompleteReview = new Review(reviewee:currentUser,complete:false)
		def notYourReview = new Review(reviewee:otherUser,complete:false)
		mockDomain(Review,[completeReview,incompleteReview,notYourReview])
		def rctrl = mockFor(ReviewService)
		def tParam
		rctrl.demand.evaluationsLeftToComplete(){t -> tParam=t;evaluations}
		def controller = new TeamMemberController()
		controller.teamMemberService = mock_current_user(currentUser)
		controller.reviewService = rctrl.createMock()
		
		def viewModel = controller.index()['teamMemberViewModel']
		assertSame evaluations,viewModel.evaluationsToComplete
		assertSame currentUser,viewModel.teamMember
		assertSame tParam,currentUser
		assertEquals viewModel.resultsToView.size(),1
		assertSame completeReview,viewModel.resultsToView[0]
		rctrl.verify()
	}
	
	void test_index_redirects_to_login_for_invalid_user(){
		throw new Exception('not implemented')
	}
	def mock_current_user(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController.createMock()
	}
}
