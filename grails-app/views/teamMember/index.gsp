<%@ page import="org.surveyresults.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${teamMemberViewModel.teamMember.name }</title>
    </head>
    <body>

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
    </body>
</html>