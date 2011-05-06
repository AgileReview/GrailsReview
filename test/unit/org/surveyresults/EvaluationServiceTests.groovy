package org.surveyresults

import grails.test.*

class EvaluationServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_when_creating_a_blank_review_all_questions_are_available_in_order() {
		//generate exactly two questions
		def reviews = []
		def responses = []
		mockDomain(Review,[])
		mockDomain(Response,[])
		mockDomain(Evaluation,[])
		mockDomain(Question,[new Question(id: 2,text:'who what'),new Question(id:1,text: 'what where')])
		mockDomain(Person,[new Person(name:'Patrick Escarcega')])
		def service = new EvaluationService()
		def review = new Review()
		def evaluation = service.createBlankEvaluation(review)
		assert(evaluation.class ==Evaluation)
		assertEquals review.evaluations.size(),1
		assertEquals evaluation.responses.size(), 2
		assertNotNull evaluation.responses.find {r -> r.question.text == 'what where'}


    }
}
