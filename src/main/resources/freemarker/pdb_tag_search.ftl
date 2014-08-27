<!doctype HTML>
<html>
<head>
<title>Search Results for ${querytag}</title>
	<link rel="stylesheet" type="text/css" href="/styles.css">
</head>
<body>
<div id="header" class="clearfix">
	<div class="menu">
		<a href="/">Project Home</a>
		<a href="/create">Create a Project</a>
	</div>
	<div class="user-options">
		<#if username??>
			Welcome ${username} <a href="/logout">Logout</a> | <a href="/">Blog Home</a>
		</#if>
	</div>
</div>

<h2> Search results for tag ${querytag}</h2>
<#if projects?has_content>
    <#list projects as project>
        <#if project.commonNames?has_content>
            <h2>
            <#list project.commonNames as commonName>
            ${commonName} 
            </#list>
            </h2> <a href="/project/${project.id}">More Details</a>
        </#if>
        <#if project.tags?has_content>
            <h4>With Tags</h4><p>
            <#list project.tags as tag>
            <a href="/search?tag=${querytag?html},${tag.name?html}">${tag.name}</a>, 
            </#list>
            </p>
        </#if>
        <hr/>
    </#list>
<#else>
<div class="error clearfix">no search results found</div>
</#if>
</body>
</html>

