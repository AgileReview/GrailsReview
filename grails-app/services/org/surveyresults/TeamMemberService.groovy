package org.surveyresults

class TeamMemberService {

    static transactional = true

    def getCurrentTeamMember(def session) {
		return session.teamMember
    }
}
