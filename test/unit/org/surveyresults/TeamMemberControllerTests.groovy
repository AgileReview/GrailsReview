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
}
