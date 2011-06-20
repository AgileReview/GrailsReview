import org.surveyresults.*
class BootStrap {

    def init = { servletContext ->
		if(!Role.count()){
			new Role(name:'Dev').save(failOnError:true)
			new Role(name:'QA').save(failOnError:true)
			new Role(name:'Manager').save(failOnError:true)
		}
		if(!TeamMember.count()){
			new TeamMember(name:'Patrick Escarcega',role:Role.findByName('Dev'),email:'pescarcega@paraport.com',password:'patrick').save(failOnError:true)
			new TeamMember(name:'Dave Perry',role:Role.findByName('Dev'),email:'dperry@paraport.com',password:'dave').save(failOnError:true)
			//new TeamMember(name:'Josh Rolstad',role:Role.findByName('Dev'),email:'jrolstad@paraport.com',password:'josh').save(failOnError:true)
			//new TeamMember(name:'Sumair Pervaiz',role:Role.findByName('Dev'),email:'spervaiz@paraport.com',password:'sumair').save(failOnError:true)
			//new TeamMember(name:'Mary Panza',role:Role.findByName('QA'),email:'mpanza@paraport.com',password:'mary').save(failOnError:true)
			//new TeamMember(name:'Michelle Wu',role:Role.findByName('QA'),email:'mwu@paraport.com',password:'michelle').save(failOnError:true)
			//new TeamMember(name:'Nathan Flint',role:Role.findByName('QA'),email:'nflint@paraport.com',password:'nathan').save(failOnError:true)
			//new TeamMember(name:'Laurie Soetanto',role:Role.findByName('QA'),email:'lsoetanto@paraport.com',password:'laurie').save(failOnError:true)
			//new TeamMember(name:'Eric Peterson',role:Role.findByName('QA'),email:'epeterson@paraport.com',password:'eric').save(failOnError:true)
			//new TeamMember(name:'Paul McCallick',role:Role.findByName('Manager'),email:'pmccallick@paraport.com',password:'paul').save(failOnError:true)
		}
		if(!Question.count()){
			new Question(text:'Works well with a team').save(failOnError:true)
			new Question(text:'Stays at the top of their profession').save(failOnError:true)
//			new Question(text:'Works with and for users').save(failOnError:true)
//			new Question(text:'Is able to self manage').save(failOnError:true)
//			new Question(text:'Stays test driven').save(failOnError:true)
//			new Question(text:'Is a great architect').save(failOnError:true)
//			new Question(text:'Manages work well').save(failOnError:true)
//			new Question(text:'Writes great code').save(failOnError:true)
//			new Question(text:'Writes great tests').save(failOnError:true)
		}
		if(!Answer.count()){
			new Answer(text:'Strongly Disagree',value:1).save(failOnError:true)
			new Answer(text:'Disagree',value:2).save(failOnError:true)
			new Answer(text:'Nuetral',value:3).save(failOnError:true)
			new Answer(text:'Agree',value:4).save(failOnError:true)
			new Answer(text:'Strongly Agree',value:5).save(failOnError:true)
		}
		if(!TeamReview.count()){
			def incompleteReview = new TeamReview(name:'inCompleteReview')
			incompleteReview.save(failOnError:true)
			def rs = new ReviewService()
			rs.evaluationService = new EvaluationService()
			TeamMember.list().each { t-> rs.createBlankReview(t,incompleteReview).save(failOnError:true)}
			
		}



    }
    def destroy = {
    }
}
