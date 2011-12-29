
<%@ page import="org.agilereview.TeamReview" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teamReview.label', default: 'TeamReview')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'teamReview.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="complete" title="${message(code: 'teamReview.complete.label', default: 'Complete')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'teamReview.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${teamReviewInstanceList}" status="i" var="teamReviewInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${teamReviewInstance.id}">${fieldValue(bean: teamReviewInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatBoolean boolean="${teamReviewInstance.complete}" /></td>
                        
                            <td>${fieldValue(bean: teamReviewInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${teamReviewInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
