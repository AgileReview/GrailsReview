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
		onlyManagerCanEditAnothersRecord 'edit()'
	}
	
	void test_non_manager_cant_update(){
		onlyManagerCanEditAnothersRecord 'update()'
	}
	
	void test_non_manager_cant_delete(){
		onlyManagerCanEditAnothersRecord 'delete()'
	}
	
	void test_non_manager_cant_create(){
		onlyManagerCanEditAnothersRecord 'create()'
	}
	
	void test_non_manager_cant_show(){
		onlyManagerCanEditAnothersRecord 'show()'
	}
	
	void test_non_manager_cant_save(){
		onlyManagerCanEditAnothersRecord 'save()'
	}

    void teamMemberCantManageOthersRecord(def action){
 		def controller = new TeamMemberController()

        def crntUser = new TeamMember(id:1,role:new Role(name:'x'))
        mockDomain(TeamMember,[new TeamMember(id:2,role:new Role(name:'x')),crntUser])
        def tmService =  mock_current_user(crntUser)
		controller.teamMemberService =  tmService
        controller.params.id = 2 //not current users id
		def code = "{->${action}}"
		def shell = new GroovyShell()
		def closure = shell.evaluate(code)
		closure.delegate = controller
		closure()

		assertEquals 401,controller.response.status
		assertEquals 'Only managers can manage other users profiles.',controller.response.errorMessage
    }

    void test_teamMember_cant_edit_others_record(){
        teamMemberCantManageOthersRecord('edit()')
    }

        void test_teamMember_cant_show_others_record(){
        teamMemberCantManageOthersRecord('show()')
    }

        void test_teamMember_cant_update_others_record(){
        teamMemberCantManageOthersRecord('update()')
    }


     void test_teamMember_can_edit_own_record(){
        teamMemberCanDoIt('edit()')
    }

    void test_teamMember_can_save_own_record(){
        teamMemberCanDoIt('save()')
    }

    void test_teamMember_can_show_own_record(){
        teamMemberCanDoIt('show()')
    }



    void teamMemberCanDoIt(def action){
 		def controller = new TeamMemberController()

        def crntUser = new TeamMember(id:1,role:new Role(name:'x'))
        mockDomain(TeamMember,[new TeamMember(id:2,role:new Role(name:'x')),crntUser])
        def tmService =  mock_current_user(crntUser)
		controller.teamMemberService =  tmService
        controller.params.id = 1 // current users id
		def code = "{->${action}}"
		def shell = new GroovyShell()
		def closure = shell.evaluate(code)
		closure.delegate = controller
		closure()

		assertEquals 200,controller.response.status
    }

	void onlyManagerCanEditAnothersRecord(def action){
		def controller = new TeamMemberController()

        controller.params.id  =2 //sets the parameter of the user being edited to something other than current user
		controller.teamMemberService = mock_current_user(new TeamMember(id:1,role:new Role(name:'x')))
		def code = "{->${action}}"
		def shell = new GroovyShell()
		def closure = shell.evaluate(code)
		closure.delegate = controller
		closure()
		assertEquals 401,controller.response.status
		assertEquals 'Only managers can manage other users profiles.',controller.response.errorMessage
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
		teamMemberServiceController.demand.getCurrentTeamMember(1..2){user}
		teamMemberServiceController.createMock()
	}
}
