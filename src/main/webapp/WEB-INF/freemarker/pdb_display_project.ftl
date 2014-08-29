<!doctype HTML>
<html>
<head>
    <title>Display Project Details</title>
    <#include "pdb_common_styles.ftl"/>
</head>
<body>
<#include "pdb_common_header.ftl"/>
    <h2>Common Names (*)</h2>
    <#if model.project.commonNames?has_content>
    <#list model.project.commonNames as commonName>
    ${commonName} 
    </#list>
    </#if>
    <br/>

    <h2>Solution Description</h2>
    <#if model.project.solutionDescription??>
    ${model.project.solutionDescription!""}
    </#if>
    <br/>

    <h2>PIDs</h2>
    <#if model.project.pids?has_content>
    <#list model.project.pids as pid>
    ${pid} 
    </#list>
    </#if>
    <br/>

    <h2>Clients</h2>
    <#if model.project.clients?has_content>
    <#list model.project.clients as client>
    ${client} 
    </#list>
    </#if>
    <br/>

    <h2>Industries</h2>
    <#if model.project.industries?has_content>
    <#list model.project.industries as industry>
    ${industry} 
    </#list>
    </#if>
    <br/>

    <h2>Markets</h2>
    <#if model.project.markets?has_content>
    <#list model.project.markets as market>
    ${market} 
    </#list>
    </#if>
    <br/>

    <h2>Unsorted Tags</h2>
    <#if model.project.tags?has_content>
    <#list model.project.tags as tag>
        ${tag.name}, 
    </#list>
    </#if>

</body>
</html>

