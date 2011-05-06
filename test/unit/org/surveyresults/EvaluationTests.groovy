package org.surveyresults

import grails.test.*

class EvaluationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_evaluation_with_all_params_creates_valid_evaluation() {
		mockDomain(Question,[new Question(id:1,text:'foo'),new Question(id:2,text:'bar')])
		mockDomain(Review,[new Review(id:1)])
		mockDomain(Evaluation,[])
		mockDomain(Response,[])
		mockDomain(Answer,[new Answer(id:1,text:'foo',value:1)])
		
		def params = [:]
		params['evaluation.review.id'] = 1
		params['evaluation.response[0].question.id'] = 1
		params['evaluation.response[0].answer.id'] = 1
		params['evaluation.response[1].question.id'] = 2
		params['evaluation.response[1].answer.id'] = 1
		
		def eval = new Evaluation(params)
		assertNotNull eval.review
		eval.save(failOnError:true)
    }
}
