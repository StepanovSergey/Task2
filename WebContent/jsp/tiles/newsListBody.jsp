<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<span class="contentNewsTitle"><bean:message key="news.news" /></span>
&gt;&gt;
<bean:message key="menu.news.list" />
<br>
<br>
<html:form action="/DeleteGroupOfNews"
	onsubmit="return deleteGroupOfNews()">
	<logic:empty name="newsForm" property="newsList">
		<bean:message key="news.noNews" />
	</logic:empty>
	<logic:iterate id="news" property="newsList" name="newsForm">
		<table class="newsListTable">
			<tr>
				<td class='newsListTableTitle'><bean:write name="news"
						property="title" /></td>
				<td><bean:write name="news" property="date"
						formatKey="date.pattern" /></td>
			</tr>
			<tr>
				<td><bean:write name="news" property="brief" /></td>
			</tr>
			<tr>
				<bean:define name="news" id="newsId" property="id" />
				<td class='newsListTableLinks' colspan="2"><html:link
						action="ViewNews" paramName="newsId" paramId="news.id">
						<bean:message key="news.list.view"></bean:message>
					</html:link> &nbsp;&nbsp;&nbsp;&nbsp; <html:link action="EditNews"
						paramName="newsId" paramId="news.id">
						<bean:message key="news.list.edit"></bean:message>
					</html:link> &nbsp;&nbsp;&nbsp;&nbsp; <html:multibox property="selectedItems">
						<bean:write name='news' property='id' />
					</html:multibox></td>
			</tr>
		</table>
	</logic:iterate>
	<logic:notEmpty name="newsForm" property="newsList">
		<table class="newsListTable">
			<tr>
				<td class="afterTableButtons"><html:submit>
						<bean:message key="news.delete" />
					</html:submit></td>
			</tr>
		</table>
	</logic:notEmpty>
</html:form>
<br>