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
  	<tr><td>Question</td>
  		<td>Your Average Score</td>
  		<td>Your Max Score</td>
  		<td>Your Min Score</td>
  	</tr>
	  <g:each var="reviewResult" in="${reviewResults}">
	  	<tr>
	  		<td>${reviewResult.question.text}</td>
	  		<td>${reviewResult.yourScore }</td>
	  		<td>${reviewResult.minAnswer }</td>
	  		<td>${reviewResult.maxAnswer }</td>
	  	</tr>
	  </g:each>
  </table>
  </div>
</body>
</html>