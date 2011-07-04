<%@ page import="org.agilereview.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${teamMemberViewModel.teamMember.name }</title>
    </head>
    <body>
    <g:if test="${flash.message}">
    	<div class="message">${flash.message}</div>
    </g:if>
	<div>Welcome ${teamMemberViewModel.teamMember.name}!</div>
    <div>Please complete reviews for the following team members:</div>
    <div>
    <g:each var="evaluation" in="${teamMemberViewModel.evaluationsToComplete}">
    <span><g:link controller="evaluation" action="update" params="[evaluationID:evaluation.id]">${evaluation.review.reviewee.name}</g:link></span><br>
    </g:each>
    </div>
    <BR>
    <div>View your results...</div>
    
    <div>
    <g:each var="review" in="${teamMemberViewModel.resultsToView}">
    <span><g:link controller="teamReview" action="results" params="[id:review.teamReview.id]">${review.teamReview.name}</g:link></span><br>
    </g:each>
    </div>
    <br>
    <div>
    <span><g:link action="changePassword">Change password</g:link></span>
    </div>
    </body>
</html>