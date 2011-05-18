<%@ page import="org.surveyresults.Evaluation" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'review.label', default: 'Evaluation')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    <g:hasErrors bean="${evaluationViewModel.evaluationInstance}">
    <div class="errors">
        <g:renderErrors bean="${evaluationViewModel.evaluationInstance}" as="list" />
    </div>
    </g:hasErrors>
    <div>Please complete review for ${evaluationViewModel.evaluationInstance.review.reviewee.name}</div>
    <g:form action="save" >

    <g:hiddenField name="id" value="${evaluationViewModel.evaluationInstance.id}"></g:hiddenField>
    <div class="dialog">
    	<table>
    	
    	<g:each var="resp" status="i" in="${evaluationViewModel.evaluationInstance.responses.sort{it.question.id}}">
    		
    		
    		<tr><td>${resp.question.id }</td><td>${resp.question.text}</td>
    		<td>
    		
    		<g:each var="answer" in="${evaluationViewModel.answers}">
    		<g:radio name="responses[${i}].answer.id" value="${answer.id}"/>${answer.value}
    		</g:each>
    		</td>
    		</tr>
    	</g:each>
    	</table>
    </div>
    <div>
    	<span>Please enter any free form comments below:</span><br>
    	<span></span><g:textArea name="comments"></g:textArea></span>
    	
    </div>
    <g:submitButton name="complete"></g:submitButton>
    </g:form>
    </body>
</html>