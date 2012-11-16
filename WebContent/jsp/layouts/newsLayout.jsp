<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>
<script type="text/javascript" src="js/initMessageVars.jsp"></script>
<script type="text/javascript" src="js/script.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css"></link>
</head>
<body>
	<!-- Header -->
	<table class="header">
		<tr>
			<td rowspan="2" class="header_title"><tiles:insert
					attribute="headerTitle" /></td>
		</tr>
		<tr>
			<td class="header_languages"><tiles:insert
					attribute="headerLang" /></td>
		</tr>
	</table>
	<!-- Menu and content -->
	<table class="menu_content">
		<!-- Menu -->
		<tr>
			<td class="menu">
				<div class="menu_div">
					<br>
					<tiles:insert attribute="menu" />
					<br>
				</div>
			</td>
			<!-- Content -->
			<td class="content"><tiles:insert attribute="content" /></td>
		</tr>
	</table>
	<!-- Footer -->
	<table class="footer">
		<tr>
			<td><tiles:insert attribute="footer" /></td>
		</tr>
	</table>
</body>
</html>