package org.agilereview

import grails.test.*

class TeamMemberControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_non_manager_cant_edit(){
		onlyManagerCanDoIt 'edit()'
	}
	
	void test_non_manager_cant_update(){
		onlyManagerCanDoIt 'update()'
	}
	
	void test_non_manager_cant_delete(){
		onlyManagerCanDoIt 'delete()'
	}
	
	void test_non_manager_cant_create(){
		onlyManagerCanDoIt 'create()'
	}
	
	void test_non_manager_cant_show(){
		onlyManagerCanDoIt 'show()'
	}
	
	void test_non_manager_cant_save(){
		onlyManagerCanDoIt 'save()'
	}
	
	void onlyManagerCanDoIt(def action){
		def controller = new TeamMemberController()
		controller.teamMemberService = mock_current_user(new TeamMember(role:new Role(name:'x')))
		def code = "{->${action}}"
		def shell = new GroovyShell()
		def closure = shell.evaluate(code)
		closure.delegate = controller
		closure()
		assertEquals 401,controller.response.status
		assertEquals 'Only managers can do this',controller.response.errorMessage
	}
	

	
	void test_logout_clears_session_and_redirects_to_login(){
		def session = [ : ]

		def controller = new TeamMemberController()
        controller.metaClass.getSession = { -> session }
		session.teamMember = 'x'
		controller.logout()
		assertNull session.teamMember
		assertEquals 'login',controller.redirectArgs.action
		
	}
	
	void test_do_change_password_changes_pass_and_redirects_to_confirm_page(){
		def session = [:]
		def tm = new TeamMember(email:'me',password:'you',name:'x',role:new Role())
        session.teamMember = tm
		mockDomain(TeamMember,[tm])

		def controller = new TeamMemberController()
        controller.metaClass.getSession = { -> session }
		controller.params.newPassword = 'me'
		controller.doChangePassword()
		assertEquals 'index',controller.redirectArgs.action
		assertEquals 'Your password has been changed.',controller.flash.message
		assertEquals 'me',TeamMember.findByName('x').password
	}

    void test_doLogin_redirects_to_teamMember_controller_when_login_successful() {
		mockDomain(TeamMember,[new TeamMember(email:'me',password:'you',name:'x',role:new Role())])
		def session = [ : ]


		def controller = new TeamMemberController()
        controller.metaClass.getSession = { -> session }
		controller.params.email = 'me'
		controller.params.password = 'you'
		controller.doLogin()

		assertEquals 'me',controller.session.teamMember.email
		assertEquals 'teamMember',controller.redirectArgs.controller
		assertEquals 'index',controller.redirectArgs.action
    }
	
	void test_doLogin_redirects_to_login_controller_when_login_wrong() {
		mockDomain(TeamMember,[new TeamMember(email:'me',password:'them',name:'x',role:new Role())])
		def session = [ : ]


		def controller = new TeamMemberController()
        controller.metaClass.getSession = { -> session }
		controller.params.email = 'me'
		controller.params.password = 'you'
		controller.doLogin()

		assertNull controller.session.teamMember

		assertEquals 'login',controller.redirectArgs.action
		assertEquals 'Incorrect user name or password.',controller.flash.message
	}
	
	void test_index_returns_a_list_of_evaluations_to_complete_and_completed_reviews(){
		def evaluations = [new Evaluation(),new Evaluation()]
		def currentUser = new TeamMember()
		def otherUser = new TeamMember()
		def completeReview = new Review(reviewee:currentUser,complete:true)
		
		def rctrl = mockFor(ReviewService)
		def tParam
		def tParam2
		rctrl.demand.evaluationsLeftToComplete(){t -> tParam=t;evaluations}
		rctrl.demand.completeReviewsForTeamMember(){t->tParam2=t;[completeReview]}
		def controller = new TeamMemberController()
		controller.teamMemberService = mock_current_user(currentUser)
		controller.reviewService = rctrl.createMock()
		
		def viewModel = controller.index()['teamMemberViewModel']
		assertSame evaluations,viewModel.evaluationsToComplete
		assertSame currentUser,viewModel.teamMember
		assertSame tParam,currentUser
		assertSame tParam2,currentUser
		assertSame completeReview,viewModel.resultsToView[0]
		rctrl.verify()
	}
	
	void test_index_redirects_to_login_for_invalid_user(){
		def controller = new TeamMemberController()
		controller.teamMemberService = mock_current_user(null)
		controller.index()
		assertEquals 'login',controller.redirectArgs.action
		
	}
	def mock_current_user(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController.createMock()
	}
}
