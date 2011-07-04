<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Agile Review" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="grailsLogo">
        <span><g:link controller="teamMember">Agile Review</g:link> - early. often.  team based.</span>
        <g:if  test="${session.teamMember}">
        	<span style="display: block; text-align: right"><g:link controller="teamMember" action="logout">logout</g:link></span></div>
       	</g:if>
        <g:layoutBody />
    </body>
</html>