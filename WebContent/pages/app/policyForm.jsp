<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!-- <link href="/style/master.css" rel="stylesheet" type="text/css" /> -->
<link href="<html:rewrite page='/style/master.css'/>" rel="stylesheet"
	type="text/css" />
<script src="<html:rewrite page='/static/js/policyFormScript.js'/>"
	type="text/javascript"></script>
<html:form action="/auth/submitPolicy">
	<table id="msgTable">
		<tr>
			<td id="errorMsg"><html:errors /></td>
		</tr>
	</table>
<p></p>
	<table id="policyFormTable">
		<tr>
			<td>
				<table id="domainTable">
								<tr>
				
				<td id="subHeading"><bean:message key="policy.form.label.domain.details" /></td>
				</tr>
					<tr>
						<td><bean:message key="policy.form.label.domain.name" /></td>
						<td><html:text property="domainName" size="25" /></td>
						<td><bean:message key="policy.form.label.domain.desc" /></td>
						<td><html:text property="domainDesc" /></td>
						<td><bean:message key="policy.form.label.userDir" /></td>
						<td>
							<table id="userDirList">
								<tr>
									<td><html:text property="userDir" value=""/></td>
									<td><input type="button" value="+"
										onclick="addDirTextField(this)" /></td>
									<td><input type="button" value="-"
										onclick="deleteDirTextField(this)" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table id="realmTable">
				<tr>
				<td id="subHeading">
				<bean:message key="policy.form.label.realm.details" />
				</td>
				</tr>
					<tr>
						<td><bean:message key="policy.form.label.realm.name" /></td>
						<td><html:text property="realmName" value=""/></td>
						<td><bean:message key="policy.form.label.realm" /></td>
						<td><html:text property="realm" value=""/></td>
						<td><bean:message key="policy.form.label.agent" /></td>
						<td><html:text property="agent" value=""/></td>
						<td><bean:message key="policy.form.label.realm.authScheme" /></td>
						<td><html:text property="authScheme" value=""></html:text></td>
						<td><bean:message key="policy.form.label.realm.enabled" /></td>
						<td><html:checkbox property="enabled" value="on"/></td>
						<td><input type="button" value="+"
							onclick="addRealmRow(this)" /></td>
						<td><input type="button" value="-"
							onclick="deleteRealmRow(this)" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
		<td>
		<table id="rulesTable">
		<tr>
			<td id="subHeading"><bean:message key="policy.form.label.ruleList" /></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<table>
					<tr>
						<td><html:multibox property="ruleList" value="allowAccess" /></td>
						<td>allowAccess</td>
					</tr>
					<tr>
						<td><html:multibox property="ruleList" value="onAuthAccept" /></td>
						<td>OnAuthAccept</td>
					</tr>
					<tr>
						<td><html:multibox property="ruleList" value="onAuthReject" /></td>
						<td>onAuthReject</td>
					</tr>
					<tr>
						<td><html:multibox property="ruleList" value="onAuthAttempt" /></td>
						<td>onAuthAttempt</td>
					</tr>
					<tr>
						<td><html:multibox property="ruleList" value="onAccessAccept" /></td>
						<td>onAccessAccept</td>
					</tr>
					<tr>
						<td><html:multibox property="ruleList" value="onAccessReject" /></td>
						<td>onAccessReject</td>
					</tr>
				</table>
			</td>
		</tr>
		</table>
		</td>
		</tr>
		

		<tr>
			<td><html:submit>Submit</html:submit></td>
		</tr>
	</table>
</html:form>