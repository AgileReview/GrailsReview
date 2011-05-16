package org.surveyresults

import grails.test.*

class EvaluationServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_when_creating_a_blank_evaluation_all_questions_are_available_in_order() {
		//generate exactly two questions
		def reviews = []
		def responses = []
		mockDomain(Review,[])
		mockDomain(Response,[])
		mockDomain(Evaluation,[])
		mockDomain(Question,[new Question(id: 2,text:'who what'),new Question(id:1,text: 'what where')])
		def service = new EvaluationService()
		
		def review = new Review()
		def evaluation = service.createBlankEvaluation(review,new TeamMember(name:'Patrick Escarcega'))
		assert(evaluation.class ==Evaluation)
		assertSame review,evaluation.review
		assertEquals evaluation.responses.size(), 2
		assertNotNull evaluation.responses.find {r -> r.question.text == 'what where'}
		//ensure that the evaluation isn't saved to the database
		assertEquals 0,Evaluation.list().size()
    }
	

}
