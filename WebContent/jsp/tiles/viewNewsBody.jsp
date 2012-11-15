<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<span class="contentNewsTitle"><bean:message key="news.news" /></span>
&gt;&gt;
<bean:message key="news.view.title" />
<br>
<br>
<bean:define name="newsForm" property="news" id="news" />
<table>
	<tr>
		<td class="newsTableTitles"><bean:message key='news.title' /></td>
		<td><bean:write name="news" property="title" /></td>
	</tr>
	<tr>
		<td><bean:message key="news.date" /></td>
		<td><bean:write name="news" property="date"
				formatKey="date.pattern" /></td>
	</tr>
	<tr>
		<td><bean:message key="news.brief" /></td>
		<td><bean:write name="news" property="brief" /></td>
	</tr>
	<tr>
		<td><bean:message key="news.content" /></td>
		<td><bean:write name="news" property="content" /></td>
	</tr>
</table>
<table class="newsListTable">
	<tr>
		<td class="afterTableButtons"><html:form action="/EditNews">
				<html:submit>
					<bean:message key="news.edit" />
				</html:submit>
				<html:hidden name="news" property="id" />
			</html:form></td>
		<td><html:form action="/DeleteNews" onsubmit="return confirmDialog()">
				<html:submit>
					<bean:message key="news.delete" />
				</html:submit>
				<html:hidden name="news" property="id" />
			</html:form></td>
	</tr>
</table>
<br>