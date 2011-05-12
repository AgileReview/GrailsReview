package org.surveyresults

import grails.test.*

class EvaluationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_validating_an_eval_with_blank_answers_fails() {
		mockDomain(Evaluation,[])
		def eval = new Evaluation(responder:new TeamMember(),review:new Review())
		eval.addToResponses(new Response(question:new Question()))
		
		assertFalse eval.validate()
		assertEquals eval.errors.getFieldError('responses').code,'incomplete.evaluation' 
		
    }
	void test_validating_an_eval_with_complete_answers_passes() {
		mockDomain(Evaluation,[])
		def eval = new Evaluation(responder:new TeamMember(),review:new Review())
		eval.addToResponses(new Response(question:new Question(),answer:new Answer()))

		assertNull eval.validate()
		
		
	}
}
