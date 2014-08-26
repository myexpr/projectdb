<!doctype HTML>
<html>
<head>
    <title>Display Project Details</title>
</head>
<body>
<a href="/">Project Home</a>     <a href="/create">Create a Project</a>
<p>

    <h2>Common Names (*)</h2>
    <#if project.commonNames?has_content>
    <#list project.commonNames as commonName>
    ${commonName} 
    </#list>
    </#if>
    <br/>

    <h2>Solution Description</h2>
    ${project.solutionDescription!""}
    <br/>

    <h2>PIDs</h2>
    <#if project.pids?has_content>
    <#list project.pids as pid>
    ${pid} 
    </#list>
    </#if>
    <br/>

    <h2>Clients</h2>
    <#if project.clients?has_content>
    <#list project.clients as client>
    ${client} 
    </#list>
    </#if>
    <br/>

    <h2>Industries</h2>
    <#if project.industries?has_content>
    <#list project.industries as industry>
    ${industry} 
    </#list>
    </#if>
    <br/>

    <h2>Markets</h2>
    <#if project.markets?has_content>
    <#list project.markets as market>
    ${market} 
    </#list>
    </#if>
    <br/>

    <h2>Unsorted Tags</h2>
    <#if project.tags?has_content>
    <#list project.tags as tag>
        ${tag.name}, 
    </#list>
    </#if>

</body>
</html>

${project.solutionDescription}
