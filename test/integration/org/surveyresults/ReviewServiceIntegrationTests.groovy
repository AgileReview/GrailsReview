package org.surveyresults

import grails.test.*

class ReviewServiceIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_reviewsToComplete_only_returns_incomplete_evaluations_for_other_candidates() {
		def thisc = new TeamMember(name:'x',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def thatc = new TeamMember(name:'y',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def otherc = new TeamMember(name:'z',password:'x',email:'x',Role: new Role(name:'x').save()).save(failOnError:true)
		def completeForThisCandidate = new Review(quarter:'x',reviewee:thisc).save(failOnError:true)
		def incompleteForThatCandidate = new Review(quarter:'x',reviewee:thatc).save(failOnError:true)
		def incompleteForThisCandidate = new Review(quarter:'x',reviewee:thisc).save(failOnError:true)
		
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
