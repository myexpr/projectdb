<!doctype HTML>
<html>
<head>
<title>Search Results for ${querytag}</title>
	<link rel="stylesheet" type="text/css" href="/styles.css">
    <link rel="stylesheet" type="text/css" href="/jqcloud.css" />
    <script type="text/javascript" src="/jquery.min.js"></script>
    <script type="text/javascript" src="/jqcloud-1.0.4.js"></script>
    <script type="text/javascript">
        var word_list = new Array(
        <#list tagFrequency as tag>
        {text: "${tag["key"]}", weight: ${tag["value"]}, link: "/search?tag=${querytag?html},${tag["key"]?html}"},
        </#list>
        { text:"", weight:0});
      $(document).ready(function() {
        $("#wordcloud").jQCloud(word_list);
      });
    </script>
    <style type="text/css">
      #wordcloud {
        margin: 30px auto;
        width: 600px;
        height: 371px;
        border: none;
      }
      #wordcloud span.w10, #wordcloud span.w9, #wordcloud span.w8, #wordcloud span.w7 {
        text-shadow: 0px 1px 1px #ccc;
      }
      #wordcloud span.w3, #wordcloud span.w2, #wordcloud span.w1 {
        text-shadow: 0px 1px 1px #fff;
      }
    </style>
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
<div id="wordcloud"></div>
<#if projects?has_content>
    Found ${projects?size} projects
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

