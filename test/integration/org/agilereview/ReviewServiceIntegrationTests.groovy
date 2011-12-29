package org.agilereview

import grails.test.*

class ReviewServiceIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void test_completeReviewsForTeamMember(){
		def tm1 = createTeamMember('right')
		def tm2 = createTeamMember('wrong')
		def tr1 = new TeamReview(name:'complete',complete:true)
		def tr2 = new TeamReview(name:'incomplete',complete:false)
		tr1.addToReviews(new Review(reviewee:tm1))
		tr1.addToReviews(new Review(reviewee:tm2))
		tr1.save(failOnError:true)
		tr2.addToReviews(new Review(reviewee:tm1))
		tr2.addToReviews(new Review(reviewee:tm2))
		tr2.save(failOnError:true)
		def rs = new ReviewService()
		def res = rs.completeReviewsForTeamMember(tm1)
		
		assertEquals res.size(),1
		
		assertEquals res[0].teamReview.name,'complete'
		
	}
	
	def createTeamMember(def name){
		def t = new TeamMember(name:name,password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
	}

    void test_reviewsToComplete_only_returns_incomplete_evaluations_for_other_candidates() {
		def thisc = new TeamMember(name:'x',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def thatc = new TeamMember(name:'y',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def otherc = new TeamMember(name:'z',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def tr = new TeamReview(name:'tr')
		tr.save()
		
		def completeForThisCandidate = new Review(reviewee:thisc)
		tr.addToReviews(completeForThisCandidate)
		completeForThisCandidate.save(failOnError:true)
		def incompleteForThatCandidate = new Review(reviewee:thatc)
		tr.addToReviews(incompleteForThatCandidate)
		incompleteForThatCandidate.save(failOnError:true)
		def incompleteForThisCandidate = new Review(reviewee:thisc)
		tr.addToReviews(incompleteForThisCandidate)
		incompleteForThisCandidate.save(failOnError:true)
		
		completeForThisCandidate.addToEvaluations(new Evaluation(responder:thatc,complete:true))
		incompleteForThatCandidate.addToEvaluations(new Evaluation(responder:thisc,complete:false))
		incompleteForThatCandidate.addToEvaluations(new Evaluation(responder:otherc,complete:false))
		incompleteForThisCandidate.addToEvaluations(new Evaluation(responder:thatc,complete:false))
		
		completeForThisCandidate.save(flush:true)
		incompleteForThatCandidate.save(flush:true)
		incompleteForThisCandidate.save(flush:true)
		
		def a = completeForThisCandidate.id
		def b = incompleteForThatCandidate.id
		def c = incompleteForThisCandidate.id

		def rs = new ReviewService()
		def results = rs.evaluationsLeftToComplete(thisc)
		
		assertEquals 1,results.size()
		def eval = results[0]
		assertEquals eval.class,Evaluation
		assertEquals eval.review.id,incompleteForThatCandidate.id

    }
}
