<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en"	class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link href="/favicon.ico" type="image/x-icon" rel=icon>
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<meta name="renderer" content="webkit">
<title>若水配置管理中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<form id="loginform" name="loginform" action="${path}/login.shtml" method="post">
		<span>账号:<input type="text" placeholder="username" name="username" value="admin" /></span>
		<span>密码:<input type="password" placeholder="password" name="password" value="123456" /></span>
		<span><a type="submit" href="javascript:checkUserForm()">登录</a></span>
	</form>
	<script type="text/javascript">
		if ("${error}" != "") {
			alert("${error}");
		};
		function checkUserForm() {
			document.loginform.submit();
		}
	</script>
</body>
</html>