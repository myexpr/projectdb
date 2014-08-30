<!doctype HTML>
<html>
<head>
	<title>Create a new Project</title>
    <#include "pdb_common_styles.ftl"/>

</head>
<body>
<#include "pdb_common_header.ftl"/>
<div class="w1000">
	<form action="/save" method="POST">
		<div class="error clearfix">
			<#list errors as error>
			${error}</br>
			</#list>
		</div>
		<div id="form-elements">
			<div class="form-row">
				<label>Common Names (*)</label>
				<input type="text" name="COMMON_NAME" value="${projectParams["COMMON_NAME"]!""}">
			</div>

			<div class="form-row">
				<label>Solution Description</label>
				<input type="text" name="SOLUTION_DESCRIPTION" value="${projectParams["SOLUTION_DESCRIPTION"]!""}">
			</div>

			<div class="form-row">
				<label>PIDs</label>
				<input type="text" name="PIDS" value="${projectParams["PIDS"]!""}">
			</div>

			<div class="form-row">
				<label>Clients</label>
				<input type="text" name="CLIENTS" value="${projectParams["CLIENTS"]!""}">
			</div>

			<div class="form-row">
				<label>Industries</label>
				<input type="text" name="INDUSTRIES" value="${projectParams["INDUSTRIES"]!""}">
			</div>

			<div class="form-row">
				<label>Markets</label>
				<input type="text" name="MARKETS" value="${projectParams["MARKETS"]!""}">
			</div>

			<div class="form-row">
				<label>Methodology (comma seperated multiple values allowed)</label>
				<textarea name="METHODOLOGY" >${projectParams["METHODOLOGY"]!""}</textarea>
			</div>

			<div class="form-row">
				<label>Collboration (comma seperated multiple values allowed)</label>
				<textarea name="COLLABORATION" >${projectParams["COLLABORATION"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Language (comma seperated multiple values allowed)</label>
				<textarea name="LANGUAGE" >${projectParams["LANGUAGE"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>IDE / Development Tools (comma seperated multiple values allowed)</label>
				<textarea name="IDE" >${projectParams["IDE"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Web Application Servers (comma seperated multiple values allowed)</label>
				<textarea name="WEB_APPLICATION_SERVER" >${projectParams["WEB_APPLICATION_SERVER"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Products (comma seperated multiple values allowed)</label>
				<textarea name="PRODUCT" >${projectParams["PRODUCT"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Frameworks (comma seperated multiple values allowed)</label>
				<textarea name="FRAMEWORK" >${projectParams["FRAMEWORK"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Persistent Storage (comma seperated multiple values allowed)</label>
				<textarea name="PERSISTENT_STORAGE" >${projectParams["PERSISTENT_STORAGE"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Version Control (comma seperated multiple values allowed)</label>
				<textarea name="VERSION_CONTROL" >${projectParams["VERSION_CONTROL"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Automated Testing Tools (comma seperated multiple values allowed)</label>
				<textarea name="AUTOMATED_TESTING" >${projectParams["AUTOMATED_TESTING"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Desktop Tools (comma seperated multiple values allowed)</label>
				<textarea name="DESKTOP_TOOLS" >${projectParams["DESKTOP_TOOLS"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Build Tools (comma seperated multiple values allowed)</label>
				<textarea name="BUILD_TOOLS" >${projectParams["BUILD_TOOLS"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Hardware (comma seperated multiple values allowed)</label>
				<textarea name="HARDWARE" >${projectParams["HARDWARE"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Monitoring (comma seperated multiple values allowed)</label>
				<textarea name="MONITORING" >${projectParams["MONITORING"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Traffic Management (comma seperated multiple values allowed)</label>
				<textarea name="TRAFFIC_MANAGERS" >${projectParams["TRAFFIC_MANAGERS"]!""}</textarea>
			</div>
			
			<div class="form-row">
				<label>Others (comma seperated multiple values allowed)</label>
				<textarea name="OTHERS" >${projectParams["OTHERS"]!""}</textarea>
			</div>
			<div class="form-row button">
				<input type="submit" value="Submit">
			</div>
		</div>
	</form>
</div>
</body>
</html>

