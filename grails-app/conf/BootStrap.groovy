import org.surveyresults.*
class BootStrap {

    def init = { servletContext ->
		if(!Role.count()){
			new Role(name:'Dev').save(failOnError:true)
			new Role(name:'QA').save(faileOnError:true)
		}
		if(!Person.count()){
			new Person(name:'Patrick Escarcega',role:Role.findByName('Dev')).save(failOnError:true)
			new Person(name:'Dave Perry',role:Role.findByName('Dev')).save(failOnError:true)
			new Person(name:'Josh Rolstad',role:Role.findByName('Dev')).save(failOnError:true)
			new Person(name:'Sumair Pervaiz',role:Role.findByName('Dev')).save(failOnError:true)
			new Person(name:'Mary Panza',role:Role.findByName('QA')).save(failOnError:true)
			new Person(name:'Michelle Wu',role:Role.findByName('QA')).save(failOnError:true)
			new Person(name:'Nathan Flint',role:Role.findByName('QA')).save(failOnError:true)
			new Person(name:'Laurie Soetanto',role:Role.findByName('QA')).save(failOnError:true)
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
			new Answer(text:'Drives the team not to do this',value:1).save(failOnError:true)
			new Answer(text:'Doesn\'t Do this',value:2).save(failOnError:true)
			new Answer(text:'Does This',value:3).save(failOnError:true)
			new Answer(text:'Does This Well',value:4).save(failOnError:true)
			new Answer(text:'Drives the team to do this',value:5).save(failOnError:true)
		}
		if(!Review.count()){
			Person.list().each {p -> new Review(person:p).save(failOnError:true)  }
		}
		if(!Response.count()){
			File file = new File('C:\\Documents and Settings\\paulm\\My Documents\\PPAStuff\\ReviewResults.2010.Q1\\Team.csv')
			def comments = 0
			def questions = []
			file.eachLine{line,number->
				
				if(number==1){
					//get questions
					def qs =  line.split(',')
					qs.eachWithIndex {q,column ->
						if(column >0){
							if(q== "Additional Comments"){
								comments = column
							}
							else{
								println('finding question:'  + q + ' at col ' + column)
								def qu = Question.findByText(q)
								if(qu == null){
									throw new Exception("unknown question" + q)
								}
								questions[column] = qu

							}
						}
							
					}
				}
				else{
					//now insert responses
					def resp =  line.split(',')
					def p = Person.findByName(resp[0])
					if(p==null){throw new Exception("unknown person at line " + number)}
					def r = Review.findByPerson(p)
					if(r==null){throw new Exception("unknown review at line " + number)}
					resp.eachWithIndex {res,col -> 
						if(col > 0 && col!= comments){
							def a = Answer.findByText(res)
							if(a==null){throw new Exception("unknown answer at line " + number + "col: " + col)}
							if(questions[col] == null){throw new Exception("unknown question at line " + number + "col: " + col)}
							new Response(review:r,answer:a,question:questions[col]).save(failOnError:true)
						}
						if(col==comments){
							new Comment(review:r,text:res).save(failOnError:true)
						}
					}
				}
			}
		}

    }
    def destroy = {
    }
}
