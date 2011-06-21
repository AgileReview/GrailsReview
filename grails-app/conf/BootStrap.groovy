import org.surveyresults.*
import grails.util.GrailsUtil
class BootStrap {

	def init = { servletContext ->
		if(!Role.count()){
			new Role(name:'Dev').save(failOnError:true)
			new Role(name:'QA').save(failOnError:true)
			new Role(name:'Manager').save(failOnError:true)
		}
		if(!Answer.count()){
			new Answer(text:'Strongly Disagree',value:1).save(failOnError:true)
			new Answer(text:'Disagree',value:2).save(failOnError:true)
			new Answer(text:'Nuetral',value:3).save(failOnError:true)
			new Answer(text:'Agree',value:4).save(failOnError:true)
			new Answer(text:'Strongly Agree',value:5).save(failOnError:true)
		}
		switch(GrailsUtil.environment){
			case ["development","test"]:
				if(!TeamMember.count()){
					new TeamMember(name:'Patrick',role:Role.findByName('Dev'),email:'patrick@p.com',password:'patrick').save(failOnError:true)
					new TeamMember(name:'Dave',role:Role.findByName('Dev'),email:'dave@d.com',password:'dave').save(failOnError:true)
				}
				if(!Question.count()){
					new Question(text:'Works well with a team').save(failOnError:true)
					new Question(text:'Stays at the top of their profession').save(failOnError:true)
				}
				if(!TeamReview.count()){
					initReviews()
				}
			case "prod":
				if(!Question.count()){
					new Question(text:'Works well with a team').save(failOnError:true)
					new Question(text:'Stays at the top of their profession').save(failOnError:true)
					new Question(text:'Works with and for users').save(failOnError:true)
					new Question(text:'Is able to self manage').save(failOnError:true)
					new Question(text:'Stays test driven').save(failOnError:true)
					new Question(text:'Is a great architect').save(failOnError:true)
					new Question(text:'Manages work well').save(failOnError:true)
					new Question(text:'Writes great code').save(failOnError:true)
					new Question(text:'Writes great tests').save(failOnError:true)
				}
		}
	}
	
	def initReviews(){
		println 'creating a dummy incomplete review'
		def incompleteReview = new TeamReview(name:'inCompleteReview')
		incompleteReview.save(failOnError:true)
		def rs = new ReviewService()
		rs.evaluationService = new EvaluationService()
		TeamMember.list().each { t-> rs.createBlankReview(t,incompleteReview).save(failOnError:true)}
		println 'creating a dummy completed team review'
		def completeReview = new TeamReview(name:'completeReview')
		completeReview.save(failOnError:true)
		rs.evaluationService = new EvaluationService()
		TeamMember.list().each { t-> rs.createBlankReview(t,completeReview).save(failOnError:true)}
		//answer all the questions
		completeReview.reviews.each { review->review.evaluations.each { eval->completeEvaluation(eval)}}
	}
	def completeEvaluation(eval) {
		def es = new EvaluationService()
		es.reviewService =  new ReviewService()
		es.reviewService.teamReviewService = new TeamReviewService()
		eval.responses.each { resp->
			resp.answer=Answer.findByText('Agree')
			resp.save(failOnError:true)
		}
		es.complete eval
	}
	def destroy = {
	}
}
