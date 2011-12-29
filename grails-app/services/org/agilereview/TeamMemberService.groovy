package org.agilereview

class TeamMemberService {

    static transactional = true

    def getCurrentTeamMember(def session) {
		def tm
		if(session.teamMember){
			tm = TeamMember.get(session.teamMember.id)
		}
		tm
    }
}
