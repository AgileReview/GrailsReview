package org.surveyresults

import grails.test.*

class EvaluationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_validating_a_complete_eval_with_blank_answers_fails() {
		mockDomain(Evaluation,[])
		def eval = new Evaluation(responder:new TeamMember(),review:new Review())
		eval.addToResponses(new Response(question:new Question()))
		eval.complete = true
		
		assertFalse eval.validate()
		assertEquals eval.errors.getFieldError('responses').code,'incomplete.evaluation' 
		
    }
	
	void test_validating_an_incomplete_eval_with_blank_answers_passes() {
		mockDomain(Evaluation,[])
		def eval = new Evaluation(responder:new TeamMember(),review:new Review())
		eval.addToResponses(new Response(question:new Question()))
		eval.complete = false
		
		assertTrue eval.validate()

		
	}
	
	void test_validating_an_eval_with_complete_answers_passes() {
		mockDomain(Evaluation,[])
		def eval = new Evaluation(responder:new TeamMember(),review:new Review())
		eval.addToResponses(new Response(question:new Question(),answer:new Answer()))
	
		assertTrue eval.validate()
	}
	
	void test_gatherResults_rolls_up_questions_and_answers(){
		mockDomain(Evaluation,[])
		mockDomain(Response,[])
		
		def p1 = new TeamMember(role:new Role(name:'qa'),name:'p1')
		def q1 = new Question(text:'q1',id:3)
		def q2 = new Question(text:'q2',id:4)
		def a1 = new Answer(value:2)
		def a2 = new Answer(value:3)
		
		def p1eval = new Evaluation(responder:p1)
		p1eval.addToResponses(new Response(question:q1,answer:a1))
		p1eval.addToResponses(new Response(question:q2,answer:a2))
		
		def res = p1eval.results
		assertEquals 2,res[3l]
		assertEquals 3,res[4l]
		assertEquals 2,res.size()
	}
	
	void test_setting_results_is_possible(){
		def e = new Evaluation()
		e.results = 'x'
		assertEquals 'x',e.results
	}
	
}
