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
    <g:if test="${teamMemberViewModel.evaluationsToComplete}">
        <div>
            You have evaluations to complete!  Click on your team member to review them:<br>
        <table>
            <tr>
                <th>Team Member</th>
                <th>Team Review</th>
            </tr>
            <g:each var="evaluation" in="${teamMemberViewModel.evaluationsToComplete}">
            <tr>
                <td><g:link controller="evaluation" action="update" params="[evaluationID:evaluation.id]">${evaluation.review.reviewee.name}</g:link></td>
                <td>${evaluation.review.teamReview.name}</td>
            </tr>
        </g:each>
        </table>

        </div>
    </g:if>
    <BR>
    <div>View your results...</div>
    
    <div>
    <g:each var="review" in="${teamMemberViewModel.resultsToView}">
    <span><g:link controller="teamReview" action="results" params="[id:review.teamReview.id]">${review.teamReview.name}</g:link></span><br>
    </g:each>
    </div>
    </body>
</html>