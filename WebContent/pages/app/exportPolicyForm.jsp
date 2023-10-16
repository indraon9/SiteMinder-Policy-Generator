<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<table id="errorMsgTable">
	<tr>
		<td><font color="red"><html:errors /></font></td>
	</tr>
</table>
<html:form action="/auth/submitExportPolicy">
<table>
	<tr>
		<td id="subHeading"><bean:message key="export.form.label.heading"/>
		</td>
	<tr>
		<td><bean:message key="export.form.label.domain.name" /></td>
		<td><html:text property="domainName" size="30"/></td>
		<td><html:submit>Export</html:submit></td>
	</tr>
</table>
</html:form>