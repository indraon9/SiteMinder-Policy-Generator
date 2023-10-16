<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<div id="apgHeadLine" border="0" cellpadding="0" cellspacing="0">
	<h1
		align="center">
		Auto Policy Generator<font size="1px;">Beta 1.1</font>
	</h1>

<ul id="menuBar">
	<li>Main
		<table>
			<tr>
				<td><a href='<html:rewrite page="/auth/policyForm.do"/>'>Create Domain</a></td>
			</tr>
			<tr>
				<td><a href="#">Update Domain</a></td>
			</tr>
			<tr>
				<td><a href="#">Delete Domain</a></td>
			</tr>
		</table>
	</li>
	<li>Migrate
	<table>
	<tr>
	<td><a href="<html:rewrite page="/auth/exportPolicyForm.do"/>">Export</a></td>
	</tr>
	<tr>
	<td><a href="<html:rewrite page="/auth/importPolicyForm.do"/>">Import</a></td>
	</tr>
	</table>
	
	</li>
</ul>
<br>
</div>
<br>
