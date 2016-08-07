<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    
    <style type="text/css">
        
        .styled-button-1 {
            -webkit-box-shadow:rgba(0,0,0,0.2) 0 1px 0 0;
            -moz-box-shadow:rgba(0,0,0,0.2) 0 1px 0 0;
            box-shadow:rgba(0,0,0,0.2) 0 1px 0 0;
            color:#333;
            background-color:#FA2;
            border-radius:5px;
            -moz-border-radius:5px;
            -webkit-border-radius:5px;
            border:none;
            font-family:'Helvetica Neue',Arial,sans-serif;
            font-size:16px;
            font-weight:700;
            height:36px;
            padding:4px 16px;
            text-shadow:#FE6 0 1px 0
        }
        
    </style>
    
    <title>Upload Page</title>
    
    <body>
        
        <center><h2>Welcome ${message} !!</h2></center>
        
        <br>
        <center><h1>Upload Files here</h1></center>        
        <center><input type="submit" class="styled-button-1" value="Upload"/></center>
        <br><br>
        <center><a href="<c:url value="http://localhost:8080/spring-security-ldap-embedded/logout" />">Logout Here !</a></center>
        
    </body>
    
</html>