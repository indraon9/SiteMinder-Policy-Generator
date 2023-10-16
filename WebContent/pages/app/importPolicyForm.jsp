<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<link href="<html:rewrite page='/style/master.css'/>" rel="stylesheet"
	type="text/css" />

<table id="msgTable">
	<tr>
		<td id="errorMsg"><html:errors /></td>
		<td id="successMsg"><html:messages id="success"/></td>
	</tr>
</table>

<br>
<html:form action="/auth/submitImportPolicy"
	enctype="multipart/form-data">
	<table id="formTables">
		<tr>
			<td id="subHeading"><bean:message
					key="import.form.label.heading" /></td>
		</tr>
		<tr>
			<td id="formElements"><bean:message key="import.form.label.import" /></td>
			<td id="formElements"><html:file property="importFile" /></td>
			<td width="55%"><html:submit>Import</html:submit></td>
		</tr>	
		<logic:notEmpty name="importPolicyFormBean" property="notFoundObjects">
				<tr>
					<td id="subHeading"><bean:message
							key="import.form.label.update.object" /></td>
				</tr>
				<logic:iterate name="importPolicyFormBean" property="notFoundObjects" id="object">
					<tr>
						<td><bean:write name="object" property="objectType" /> name
							: <font color="red" size="3px"><bean:write name="object"
									property="oldObjName" /> </font>
						<html:text name="object" property="updatedObjName" indexed="true"/></td>
					</tr>
					<html:hidden name="object" property="objectType" indexed="true"/>
					<html:hidden name="object" property="oldObjName" indexed="true" />
					<%-- <html:hidden property="notFoundObjects" name="object"/> --%>
				</logic:iterate>
		</logic:notEmpty>
	</table>
</html:form>