package org.surveyresults

class TeamMemberService {

    static transactional = true

    def getCurrentTeamMember(def session) {
		//return session.teamMember
		return TeamMember.findByNameAndRole('Patrick Escarcega',Role.findByName('Dev'))
    }
}
