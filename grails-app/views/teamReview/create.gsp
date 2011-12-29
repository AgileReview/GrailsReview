

<%@ page import="org.agilereview.TeamReview" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teamReview.label', default: 'TeamReview')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${teamReviewInstance}">
            <div class="errors">
                <g:renderErrors bean="${teamReviewInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="complete"><g:message code="teamReview.complete.label" default="Complete" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamReviewInstance, field: 'complete', 'errors')}">
                                    <g:checkBox name="complete" value="${teamReviewInstance?.complete}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="teamReview.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamReviewInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${teamReviewInstance?.name}" />
                                </td>
                            </tr>
                            <g:each var="teamMember" status="i" in="${teamMembers}">
                                <tr><td><g:checkBox name="teamMembers" value="${teamMember.id}"/></td><td>${teamMember.name}</td></tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
