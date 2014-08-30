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

<div class="w1000">
    <h1>Search results for tag "<span class="red">${querytag}</span>"</h1>
    <div id="wordcloud"></div>
    <div class="search-results">
        <#if projects?has_content>
            <h2>Found "<span class="red">${projects?size}</span>" projects</h2>

            <ul class="project-details">
                <#list projects as project>
                    <li>
                        <#if project.commonNames?has_content>
                            <h3>
                                <#list project.commonNames as commonName>
                                ${commonName} 
                                </#list>
                                <a class="more-details" href="/project/${project.id}">more details</a>
                            </h3>
                        </#if>
                        <#if project.tags?has_content>
                            <div class="tags">
                                <#list project.tags as tag>
                                    <a href="/search?tag=${querytag?html},${tag.name?html}">${tag.name}</a>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </ul>
        <#else>
        <div class="error clearfix">no search results found</div>
        </#if>
    </div>
</div>
</body>
</html>

