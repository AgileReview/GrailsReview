import org.surveyresults.*
class BootStrap {

    def init = { servletContext ->
		if(!Role.count()){
			new Role(name:'Dev').save(failOnError:true)
			new Role(name:'QA').save(failOnError:true)
			new Role(name:'Manager').save(failOnError:true)
		}
		if(!TeamMember.count()){
			new TeamMember(name:'Patrick Escarcega',role:Role.findByName('Dev')).save(failOnError:true)
			new TeamMember(name:'Dave Perry',role:Role.findByName('Dev')).save(failOnError:true)
			new TeamMember(name:'Josh Rolstad',role:Role.findByName('Dev')).save(failOnError:true)
			new TeamMember(name:'Sumair Pervaiz',role:Role.findByName('Dev')).save(failOnError:true)
			new TeamMember(name:'Mary Panza',role:Role.findByName('QA')).save(failOnError:true)
			new TeamMember(name:'Michelle Wu',role:Role.findByName('QA')).save(failOnError:true)
			new TeamMember(name:'Nathan Flint',role:Role.findByName('QA')).save(failOnError:true)
			new TeamMember(name:'Laurie Soetanto',role:Role.findByName('QA')).save(failOnError:true)
			new TeamMember(name:'Eric Peterson',role:Role.findByName('QA')).save(failOnError:true)
			new TeamMember(name:'Paul McCallick',role:Role.findByName('Manager')).save(failOnError:true)
		}
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
		if(!Answer.count()){
			new Answer(text:'Strongly Disagree',value:1).save(failOnError:true)
			new Answer(text:'Disagree',value:2).save(failOnError:true)
			new Answer(text:'Nuetral',value:3).save(failOnError:true)
			new Answer(text:'Agree',value:4).save(failOnError:true)
			new Answer(text:'Strongly Agree',value:5).save(failOnError:true)
		}
		if(!Review.count()){
			TeamMember.findAllByRole(Role.findByName('Dev')).each {p -> new Review(reviewee:p,quarter:'2nd Quarter 2011').save(failOnError:true)  }
			TeamMember.findAllByRole(Role.findByName('QA')).each {p -> new Review(reviewee:p,quarter:'2nd Quarter 2011').save(failOnError:true)  }
		}


    }
    def destroy = {
    }
}
