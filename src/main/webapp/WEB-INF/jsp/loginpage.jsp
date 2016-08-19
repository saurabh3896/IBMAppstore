<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Mirrored from seantheme.com/color-admin-v1.7/admin/html/login_v2.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Apr 2015 11:01:45 GMT -->
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>IBM AppStore | Login Page</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	
	<!-- ================== BEGIN BASE CSS STYLE ================== -->
        <!-- <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"/></link> -->
        <link rel="shortcut icon" href='<c:url value="/resources/img/favicon.ico"/>'/>
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet"></link>
        <link href="<c:url value='/resources/plugins/jquery-ui.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/plugins/bootstrap.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/plugins/font-awesome.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/css/animate.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/css/style.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/css/style-responsive.min.css' />" rel="stylesheet" /></link>
        <link href="<c:url value='/resources/css/theme/default.css' />" rel="stylesheet" id="theme"/></link>
        
        <style>
            .msg {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #31708f;
                background-color: #d9edf7;
                border-color: #bce8f1;
            }
            
            .error {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #a94442;
                background-color: #f2dede;
                border-color: #ebccd1;
            }
        </style>
	<!-- ================== END BASE CSS STYLE ================== -->
	
	<!-- ================== BEGIN BASE JS ================== -->
	<script src="<c:url value="/resources/plugins/pace.min.js" />"></script>
	<!-- ================== END BASE JS ================== -->
    </head>
    
    <body class="pace-top">
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<div class="login-cover">
            <div class="login-cover-image"><img src="<c:url value="/resources/img/bg-1.jpg" />" data-id="login-cover-image" alt="" /></div>
	    <div class="login-cover-bg"></div>
	</div>
	<!-- begin #page-container -->
	<div id="page-container" class="fade">
	    <!-- begin login -->
        <div class="login login-v2" data-pageload-addclass="animated fadeIn">
            <!-- begin brand -->
            <div class="login-header">
                <div class="brand">
                    <span class="logo"></span> IBM AppStore
                    <small>Refining WorkSpace</small>
                </div>
                <div class="icon">
                    <i class="fa fa-sign-in"></i>
                </div>
            </div>
            <!-- end brand -->
            <div class="login-content">
                
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                    
                <form action="j_spring_security_check" method="POST" class="margin-bottom-0">
                    <div class="form-group m-b-20">
                        <input type="text" name='j_username' class="form-control input-lg" placeholder="User Name" />
                    </div>
                    <div class="form-group m-b-20">
                        <input type="password" name='j_password' class="form-control input-lg" placeholder="Password" />
                    </div>
                    <div class="checkbox m-b-20">
                        <label>
                            <input type="checkbox" /> Remember Me
                        </label>
                    </div>
                    <div class="login-buttons">
                        <button type="submit" class="btn btn-success btn-block btn-lg">Sign me in</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- end login -->
        
	</div>
	<!-- end page container -->
	
	<!-- ================== BEGIN BASE JS ================== -->
        <script src="<c:url value="/resources/plugins/jquery-1.9.1.min.js" />"></script>
	<script src="<c:url value="/resources/plugins/jquery-migrate-1.1.0.min.js" />"></script>
        <script src="<c:url value="/resources/plugins/jquery-ui.min.js" />"></script>
        <script src="<c:url value="/resources/plugins/bootstrap.min.js" />"></script>
        
        <script src="<c:url value="/resources/plugins/jquery.slimscroll.min.js" />"></script>
        <script src="<c:url value="/resources/plugins/jquery.cookie.js" />"></script>
	<!-- ================== END BASE JS ================== -->
	
	<!-- ================== BEGIN PAGE LEVEL JS ================== -->
        <script src="<c:url value="/resources/js/login-v2.demo.min.js" />"></script>
        <script src="<c:url value="/resources/js/apps.min.js" />"></script>
	<!-- ================== END PAGE LEVEL JS ================== -->

	<script>
            $(document).ready(function() {
		App.init();
		LoginV2.init();
            });
	</script>
        
	<script>
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','../../../../www.google-analytics.com/analytics.js','ga');
    
            ga('create', 'UA-53034621-1', 'auto');
            ga('send', 'pageview');
        </script>
        
    </body>

</html>
