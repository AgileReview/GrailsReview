package org.surveyresults

import grails.test.*

class ReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_reviewsToComplete_only_returns_incomplete_for_other_candidates() {
		def r1 = new Review(quarter:'complete',id:1,)
		def r2 = new Review(quarter:'complete',id:2)
		def r3 = new Review(quarter:'incomplete',id:3)
		registerMetaClass Review
		Review.metaClass.static.withCriteria = {[r1,r2]} //completed
		def param
		def teamMember = new TeamMember()
		Review.metaClass.static.findAllByRevieweeNotEqual = {t->param=t;return	[r1,r2,r3]}	
		def inc =  new ReviewService().reviewsLeftToComplete(teamMember)

		assertNotNull inc.find(){r->r.quarter=='incomplete'}
		assertNull inc.find(){r->r.quarter == 'complete'}
		assertSame param,teamMember
    }
	
	void test_reviewsForOtherTeamMembers_does_not_return_reviews_for_team_member(){
		def t = new TeamMember(name:'foo')
		def t2 = new TeamMember(name:'bar')
		def r1 = new Review(reviewee:t,id:1,)
		def r2 = new Review(reviewee:t,id:2)
		def r3 = new Review(reviewee:t2,id:3)
		mockDomain(Review,[r1,r2,r3])
		def rs = new ReviewService().reviewsForOtherTeamMembers(t2)
		assertNotNull rs.find(){r->r.reviewee.name=='foo'}
		assertNull rs.find(){r->r.reviewee.name=='bar'}
		
	}
}
