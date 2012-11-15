<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<p class="menu_title">
	<bean:message key="menu.news.title" />
</p>
<div class="menu_ul">
	<ul>
		<li><html:link action="NewsList">
				<bean:message key="menu.news.list" />
			</html:link></li>
		<li><html:link action="/AddNews">
				<bean:message key="menu.news.add" />
			</html:link></li>
	</ul>
</div>