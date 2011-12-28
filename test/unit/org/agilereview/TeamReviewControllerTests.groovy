package org.agilereview

import grails.test.*
import groovy.mock.interceptor.MockFor

class TeamReviewControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_save_calls_service_saves_returns_new_teamreview(){
        def trsCtrl = mockFor(TeamReviewService)
        def saved = false
        mockDomain(TeamReview,[])
        def tr = new TeamReview()
        def nameParam
        trsCtrl.demand.createTeamReview(){n->nameParam=n;tr.name='x';tr}

        def controller = new TeamReviewController()
        controller.metaClass.message = { Map p -> return "foo" }
        controller.params.name = 'name'
        controller.teamReviewService = trsCtrl.createMock()
        def res = controller.save()
        assertEquals 'show',controller.redirectArgs.action
        assertEquals nameParam,'name'
        assertNotNull tr.id
        trsCtrl.verify()

    }

    //void test_save_

    void test_results_gathers_up_results_from_service() {
		def currentUser = new TeamMember(id:1)
		def tmCtrl = mockCurrentUser(currentUser)
		def trCtrl = mockFor(TeamReviewService)
		def tr =  new TeamReview(id:1)
		mockDomain(TeamReview,[tr])
		mockDomain(TeamMember,[currentUser])
		def answers = [new Answer(),new Answer()]
		mockDomain(Answer,answers)
		def trParam
		def tmParam
		def trParam2
		def tmParam2
		def revResult = []
		def cmts = []
		trCtrl.demand.resultsForTeamMember(){a,b->trParam=a;tmParam=b;revResult}
		trCtrl.demand.commentsForTeamMember(){a,b->trParam2=a;tmParam2=b;cmts}
		
		def controller = new TeamReviewController()
		controller.teamMemberService = tmCtrl.createMock()
		controller.teamReviewService = trCtrl.createMock()
		controller.params.id = 1
		def result = controller.results()
		assertEquals result['answers'],answers
		assertSame result['reviewResults'],revResult
		assertSame result['comments'],cmts
		
		trCtrl.verify()
		assertSame tr,trParam
		assertSame currentUser,tmParam
		assertSame tr,trParam2
		assertSame currentUser,tmParam2
		
		tmCtrl.verify()
    }
	
	def mockCurrentUser(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController
	}
}
