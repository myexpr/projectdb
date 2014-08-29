<div id="header" class="clearfix">
	<div class="menu">
		<a href="/home">Project Home</a>
		<a href="/create">Create a Project</a>
	</div>
	<div class="user-options">
		<#if username??>
			Welcome ${username} <a href="/logout">Logout</a> | <a href="/">Blog Home</a>
		</#if>
	</div>
</div>

