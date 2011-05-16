package org.surveyresults

import grails.test.*

class ReviewIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFind_reviews_by_evaluator() {

		def role = new Role(name:'x').save()

        def p1 = new TeamMember(name:'x',password:'x',email:'x',role: role).save()
        def p2 = new TeamMember(name:'y',password:'x',email:'x',role: role).save()
        def p3 = new TeamMember(name:'z',password:'x',email:'x',role: role).save()
        def r1 = new Review(quarter:'x',reviewee:p1)
        r1.addToEvaluations(new Evaluation(responder:p2))
        r1.save(failOnError:true)
        def r1id = r1.id
        def r2 = new Review(quarter:'y',reviewee:p3)
        r2.addToEvaluations(new Evaluation(responder:p2))
        r2.save(failOnError:true)		
		def r3 = new Review(quarter:'z',reviewee:p3)
		r3.addToEvaluations(new Evaluation(responder:p3))
		r3.save(flush:true)
		
	   def reviews = Review.withCriteria(){
		   evaluations{
			   eq("responder.id",p2.id)
		   }
	   }
	   assertEquals reviews.size(),2
	   assertNull reviews.find {r-> r.quarter == 'z' }     
		
	}
}
