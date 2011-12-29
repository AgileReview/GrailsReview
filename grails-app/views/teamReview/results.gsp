<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Review Results</title>
</head>
<body>
  <div class="body">
  <table>
  	<tr><th>Question</th>
  		<th>Your Average</th>
  		<th>Role Average</th>
  		<th>Team Average</th>
  		<th>Your Max Score</th>
  		<th>Your Min Score</th>
  	</tr>
	  <g:each var="reviewResult" in="${reviewResults}">
	  	<tr>
	  		<td>${reviewResult.question.text}</td>
	  		<td>${reviewResult.yourScore }</td>
	  		<td>${reviewResult.roleAverage }</td>
	  		<td>${reviewResult.teamAverage }</td>
	  		<td>${reviewResult.maxAnswer }</td>
	  		<td>${reviewResult.minAnswer }</td>
	  	</tr>
	  </g:each>
  </table>
  
  <table>
  	<tr><th>Comments</th></tr>
  	<g:each var="comment" in="${comments}">
  	<tr><td>${comment }</td></tr>
  	</g:each>
  </table>
  <br>
  Answer Key:<br>
  <table>
  	<tr>
  		<th>Answer</th>
  		<th>Value</th>
  	</tr>
  	<g:each var="answer" in="${answers}">
  	<tr>
  		<td>${ answer.text }</td>
  		<td>${ answer.value }</td>
  	</tr>
  	</g:each>
  </table>
  </div>
</body>
</html>