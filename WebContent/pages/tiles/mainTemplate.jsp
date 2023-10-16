<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<link href="<html:rewrite page='/style/master.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<html:rewrite page='/style/menuBar.css'/>" rel="stylesheet"
	type="text/css" />
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<table id="mainTable">
		<tr>
			<td><tiles:insert attribute="header" /></td>
		</tr>
		<tr>
			<td style="padding-top: 10px;"><tiles:insert attribute="body" /></td>
		</tr>
		<tr>
			<td><tiles:insert attribute="footer" /></td>
		</tr>

	</table>

</body>
</html>