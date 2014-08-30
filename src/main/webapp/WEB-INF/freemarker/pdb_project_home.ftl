<!DOCTYPE html>
<html>
	<head>
		<title>ProuctDB Home</title>
		<#include "pdb_common_styles.ftl"/>
		<script type="text/javascript">
				var word_list = new Array(
				<#list model["tagFrequency"] as tag>
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
<#include "pdb_common_header.ftl"/>
	<div class="w1000">
		<h1>Tracking "<span class="red">${activeProjectCount}</span>" Projects</h1>
		<div id="wordcloud"></div>
	</div>
</body>
</html>
