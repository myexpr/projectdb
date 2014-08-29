<!doctype HTML>
<html>
<head>
<title>Search Results for ${model.querytag}</title>
    <#include "pdb_common_styles.ftl"/>
    <script type="text/javascript">
        var word_list = new Array(
        <#list model.tagFrequency as tag>
        {text: "${tag["key"]}", weight: ${tag["value"]}, link: "/search?tag=${model.querytag?html},${tag["key"]?html}"},
        </#list>
        { text:"", weight:0});
      $(document).ready(function() {
        $("#wordcloud").jQCloud(word_list);
      });
    </script>
</head>
<body>
<#include "pdb_common_header.ftl"/>

<h2> Search results for tag ${model.querytag}</h2>
<div id="wordcloud"></div>
<#if model.projects?has_content>
    Found ${model.projects?size} projects
    <#list model.projects as project>
        <#if project.commonNames?has_content>
            <h2>
            <#list project.commonNames as commonName>
            ${commonName} 
            </#list>
            </h2> <a href="/project/${project.id?html}">More Details</a>
        </#if>
        <#if project.tags?has_content>
            <h4>With Tags</h4><p>
            <#list project.tags as tag>
            <a href="/search?tag=${model.querytag?html},${tag.name?html}">${tag.name}</a>, 
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

