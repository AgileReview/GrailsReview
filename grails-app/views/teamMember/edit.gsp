

<%@ page import="org.agilereview.TeamMember" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teamMember.label', default: 'TeamMember')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${teamMemberInstance}">
            <div class="errors">
                <g:renderErrors bean="${teamMemberInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${teamMemberInstance?.id}" />
                <g:hiddenField name="version" value="${teamMemberInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="teamMember.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamMemberInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${teamMemberInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="teamMember.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamMemberInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${teamMemberInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="password"><g:message code="teamMember.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamMemberInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" value="${teamMemberInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="role"><g:message code="teamMember.role.label" default="Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: teamMemberInstance, field: 'role', 'errors')}">
                                    <g:select name="role.id" from="${org.agilereview.Role.list()}" optionValue="name" optionKey="id" value="${teamMemberInstance?.role?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
