<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html:link action="ChangeLocale.do?lang=en">
	<bean:message key="header.lang.eng" />
</html:link>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:link action="ChangeLocale.do?lang=ru">
	<bean:message key="header.lang.rus" />
</html:link>