<!doctype HTML>
<html>
<head>
	<title>Create a new Project</title>
	<link rel="stylesheet" type="text/css" href="/styles.css">
</head>
<body>
<div id="header" class="clearfix">
	<div class="menu">
		<a href="/">Project Home</a> <a href="/create">Create a Project</a>
	</div>
	<div class="user-options">
		<#if username??>
			Welcome ${username} <a href="/logout">Logout</a> | <a href="/">Blog Home</a>
		</#if>
	</div>
</div>
<form action="/save" method="POST">
	<div class="error clearfix">
		<#list errors as error>
		${error}</br>
		</#list>
	</div>
	<div id="form-elements">
		<h2>Common Names (*)</h2>
		<input type="text" name="COMMON_NAME" size="120" value="${projectParams["COMMON_NAME"]!""}"><br>

		<h2>Solution Description</h2>
		<input type="text" name="SOLUTION_DESCRIPTION" size="120" value="${projectParams["SOLUTION_DESCRIPTION"]!""}"><br>

		<h2>PIDs</h2>
		<input type="text" name="PIDS" size="120" value="${projectParams["PIDS"]!""}"><br>

		<h2>Clients</h2>
		<input type="text" name="CLIENTS" size="120" value="${projectParams["CLIENTS"]!""}"><br>

		<h2>Industries</h2>
		<input type="text" name="INDUSTRIES" size="120" value="${projectParams["INDUSTRIES"]!""}"><br>

		<h2>Markets</h2>
		<input type="text" name="MARKETS" size="120" value="${projectParams["MARKETS"]!""}"><br>

		<h2>Methodology (comma seperated multiple values allowed)</h2>
		<textarea name="METHODOLOGY" cols="120" rows="5">${projectParams["METHODOLOGY"]!""}</textarea><br>

		<h2>Collboration (comma seperated multiple values allowed)</h2>
		<textarea name="COLLABORATION" cols="120" rows="5">${projectParams["COLLABORATION"]!""}</textarea><br>
		
		<h2>Language (comma seperated multiple values allowed)</h2>
		<textarea name="LANGUAGE" cols="120" rows="5">${projectParams["LANGUAGE"]!""}</textarea><br>
		
		<h2>IDE / Development Tools (comma seperated multiple values allowed)</h2>
		<textarea name="IDE" cols="120" rows="5">${projectParams["IDE"]!""}</textarea><br>
		
		<h2>Web Application Servers (comma seperated multiple values allowed)</h2>
		<textarea name="WEB_APPLICATION_SERVER" cols="120" rows="5">${projectParams["WEB_APPLICATION_SERVER"]!""}</textarea><br>
		
		<h2>Products (comma seperated multiple values allowed)</h2>
		<textarea name="PRODUCT" cols="120" rows="5">${projectParams["PRODUCT"]!""}</textarea><br>
		
		<h2>Frameworks (comma seperated multiple values allowed)</h2>
		<textarea name="FRAMEWORK" cols="120" rows="5">${projectParams["FRAMEWORK"]!""}</textarea><br>
		
		<h2>Persistent Storage (comma seperated multiple values allowed)</h2>
		<textarea name="PERSISTENT_STORAGE" cols="120" rows="5">${projectParams["PERSISTENT_STORAGE"]!""}</textarea><br>
		
		<h2>Version Control (comma seperated multiple values allowed)</h2>
		<textarea name="VERSION_CONTROL" cols="120" rows="5">${projectParams["VERSION_CONTROL"]!""}</textarea><br>
		
		<h2>Automated Testing Tools (comma seperated multiple values allowed)</h2>
		<textarea name="AUTOMATED_TESTING" cols="120" rows="5">${projectParams["AUTOMATED_TESTING"]!""}</textarea><br>
		
		<h2>Desktop Tools (comma seperated multiple values allowed)</h2>
		<textarea name="DESKTOP_TOOLS" cols="120" rows="5">${projectParams["DESKTOP_TOOLS"]!""}</textarea><br>
		
		<h2>Build Tools (comma seperated multiple values allowed)</h2>
		<textarea name="BUILD_TOOLS" cols="120" rows="5">${projectParams["BUILD_TOOLS"]!""}</textarea><br>
		
		<h2>Hardware (comma seperated multiple values allowed)</h2>
		<textarea name="HARDWARE" cols="120" rows="5">${projectParams["HARDWARE"]!""}</textarea><br>
		
		<h2>Monitoring (comma seperated multiple values allowed)</h2>
		<textarea name="MONITORING" cols="120" rows="5">${projectParams["MONITORING"]!""}</textarea><br>
		
		<h2>Traffic Management (comma seperated multiple values allowed)</h2>
		<textarea name="TRAFFIC_MANAGERS" cols="120" rows="5">${projectParams["TRAFFIC_MANAGERS"]!""}</textarea><br>
		
		<h2>Others (comma seperated multiple values allowed)</h2>
		<textarea name="OTHERS" cols="120" rows="5">${projectParams["OTHERS"]!""}</textarea><br>
		<p>
		<input type="submit" value="Submit">
	</div>
</form>
</body>
</html>

