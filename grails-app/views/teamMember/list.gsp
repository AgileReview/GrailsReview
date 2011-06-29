
<%@ page import="org.surveyresults.TeamMember" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teamMember.label', default: 'TeamMember')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'teamMember.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'teamMember.email.label', default: 'Email')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'teamMember.name.label', default: 'Name')}" />
                        
                           
                        
                            <th><g:message code="teamMember.role.label" default="Role" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${teamMemberInstanceList}" status="i" var="teamMemberInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${teamMemberInstance.id}">${fieldValue(bean: teamMemberInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: teamMemberInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: teamMemberInstance, field: "name")}</td>
                        

                        
                            <td>${fieldValue(bean: teamMemberInstance, field: "role.name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${teamMemberInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
