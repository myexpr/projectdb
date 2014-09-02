<!doctype HTML>
<html>
<head>
	<title>Display Project Details</title>
	<#include "pdb_common_styles.ftl"/>
</head>
<body>
<#include "pdb_common_header.ftl"/>
	<div class="w1000">
		<div id="form-elements">
			<div class="form-row">
				<label>Common Names (*)</label>
				<p>
					<#if model.project.commonNames?has_content>
					<#list model.project.commonNames as commonName>
					${commonName} 
					</#list>
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>Solution Description</label>
				<p>
					<#if model.project.solutionDescription??>
					${model.project.solutionDescription!""}
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>PIDs</label>
				<p>
					<#if model.project.pids?has_content>
					<#list model.project.pids as pid>
					${pid} 
					</#list>
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>Clients</label>
				<p>
					<#if model.project.clients?has_content>
					<#list model.project.clients as client>
					${client} 
					</#list>
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>Industries</label>
				<p>
					<#if model.project.industries?has_content>
					<#list model.project.industries as industry>
					${industry} 
					</#list>
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>Markets</label>
				<p>
					<#if model.project.markets?has_content>
					<#list model.project.markets as market>
					${market} 
					</#list>
					</#if>
				</p>
			</div>

			<div class="form-row">
				<label>Unsorted Tags</label>
				<p>
					<#if model.project.tags?has_content>
					<#list model.project.tags as tag>
					${tag.name}, 
					</#list>
					</#if>
				</p>
			</div>
		</div>
	</div>

</body>
</html>

