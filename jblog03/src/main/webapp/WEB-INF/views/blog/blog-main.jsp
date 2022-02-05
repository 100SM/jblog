<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${post.title }</h4>
					<p>${post.contents }
					<p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${map.list }" var="postVo" varStatus="status">
						<li><a href="">${postVo.title }</a>
						<span>${postVo.regDate }</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo}">
			</div>
		</div>
		<c:import url="/WEB-INF/views/blog/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>