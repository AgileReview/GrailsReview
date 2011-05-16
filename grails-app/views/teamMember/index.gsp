<%@ page import="org.surveyresults.TeamMember" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${teamMemberViewModel.teamMember.name }</title>
    </head>
    <body>

    <div>Please complete reviews for the following team members:</div>
    <div>
    <g:each var="review" in="${teamMemberViewModel.reviewsToComplete}">
    <span><g:link controller="evaluation" action="create" params="[reviewID:review.id]">${review.reviewee.name}</g:link></span><br>
    </g:each>
    
    </div>
    </body>
</html>