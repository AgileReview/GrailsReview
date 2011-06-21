package org.surveyresults

import grails.test.*

class TeamReviewControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_results_gathers_up_results_from_service() {
		def currentUser = new TeamMember(id:1)
		def tmCtrl = mockCurrentUser(currentUser)
		def trCtrl = mockFor(TeamReviewService)
		def tr =  new TeamReview(id:1)
		mockDomain(TeamReview,[tr])
		mockDomain(TeamMember,[currentUser])
		def trParam
		def tmParam
		def revResult = []
		trCtrl.demand.resultsForTeamMember(){a,b->trParam=a;tmParam=b;revResult}
		
		def controller = new TeamReviewController()
		controller.teamMemberService = tmCtrl.createMock()
		controller.teamReviewService = trCtrl.createMock()
		controller.params.id = 1
		def result = controller.results()
		
		assertSame result['reviewResults'],revResult
		
		trCtrl.verify()
		assertSame tr,trParam
		assertSame currentUser,tmParam
		
		tmCtrl.verify()
    }
	
	def mockCurrentUser(def user){
		def teamMemberServiceController = mockFor(TeamMemberService)
		teamMemberServiceController.demand.getCurrentTeamMember(){user}
		teamMemberServiceController
	}
}
