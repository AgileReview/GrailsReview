package org.agilereview

class TeamMember {
	
	String name
	Role role
	String email
	String password

    static mapping = {
        password column: '`password`'
    }
    static constraints = {
    }
}
