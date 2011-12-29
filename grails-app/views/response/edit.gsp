

<%@ page import="org.agilereview.Response" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'response.label', default: 'Response')}" />
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
            <g:hasErrors bean="${responseInstance}">
            <div class="errors">
                <g:renderErrors bean="${responseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${responseInstance?.id}" />
                <g:hiddenField name="version" value="${responseInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="answer"><g:message code="response.answer.label" default="Answer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: responseInstance, field: 'answer', 'errors')}">
                                    <g:select name="answer.id" from="${org.agilereview.Answer.list()}" optionKey="id" value="${responseInstance?.answer?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="question"><g:message code="response.question.label" default="Question" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: responseInstance, field: 'question', 'errors')}">
                                    <g:select name="question.id" from="${org.agilereview.Question.list()}" optionKey="id" value="${responseInstance?.question?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="review"><g:message code="response.review.label" default="Review" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: responseInstance, field: 'review', 'errors')}">
                                    <g:select name="review.id" from="${org.agilereview.Review.list()}" optionKey="id" value="${responseInstance?.review?.id}"  />
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
