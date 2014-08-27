<!DOCTYPE html>
<html>
  <head>
    <title>ProuctDB Home</title>
    <link rel="stylesheet" type="text/css" href="/jqcloud.css" />
    <script type="text/javascript" src="/jquery.min.js"></script>
    <script type="text/javascript" src="/jqcloud-1.0.4.js"></script>
	<link rel="stylesheet" type="text/css" href="/styles.css">
    <script type="text/javascript">
        var word_list = new Array(
        <#list tagFrequency as tag>
            {text: "${tag["key"]}", weight: ${tag["value"]}, link: "/search?tag=${tag["key"]?html}"},
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
		<a href="/">Project Home</a> <a href="/create">Create a Project</a>
	</div>
	<div class="user-options">
		<#if username??>
			Welcome ${username} <a href="/logout">Logout</a> | <a href="/">Blog Home</a>
		</#if>
	</div>
</div>

<h2>Tracking ${activeProjectCount} Projects</h2>
<div id="wordcloud"></div>
</body>
</html>
