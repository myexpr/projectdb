
<!DOCTYPE html>
<html>
  <head>
    <title>jQCloud Demo</title>
    <link rel="stylesheet" type="text/css" href="jqcloud.css" />
    <script type="text/javascript" src="jquery.min.js"></script>
    <script type="text/javascript" src="jqcloud-1.0.4.js"></script>
    <script type="text/javascript">
        var word_list = new Array(
        <#list tagFrequency as tag>
            {text: "${tag["key"]}", weight: ${tag["value"]}, link: "/tag/${tag["key"]}"},
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
      #footer {
        font-size: 13px;
        color: #aaa;
        text-align: center;
        width: 600px;
        margin: 30px auto;
        padding: 0.5em;
        text-shadow: 0px 1px 1px #fff;
        border-top: 1px dotted #ddd;
      }
    </style>
  </head>
  <body>
    <div id="wordcloud"></div>
  </body>
</html>

