<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>킹받는 404</title>
  <style>
    body {
      background-color: #ffebee;
      font-family: 'Arial', sans-serif;
      text-align: center;
      padding: 5%;
      color: #d32f2f;
    }

    .emoji {
      font-size: 120px;
      animation: shake 0.8s infinite;
    }

    h1 {
      font-size: 60px;
      margin-bottom: 10px;
    }

    h2 {
      font-size: 30px;
      margin-bottom: 30px;
    }

    .button {
      display: inline-block;
      padding: 12px 24px;
      background-color: #d32f2f;
      color: white;
      border-radius: 8px;
      text-decoration: none;
      font-weight: bold;
      transition: background-color 0.3s ease;
    }

    .button:hover {
      background-color: #b71c1c;
    }

    @keyframes shake {
      0% { transform: translate(0, 0); }
      25% { transform: translate(-5px, 0); }
      50% { transform: translate(5px, 0); }
      75% { transform: translate(-5px, 0); }
      100% { transform: translate(0, 0); }
    }
  </style>
</head>
<body>
  <div class="emoji">😡</div>
  <h1>404 에러!</h1>
  <h2>여긴 도대체 왜 온 거야? 🤬</h2>
  <p>페이지가 사라졌거나, 애초에 없던 거임;;</p>
  <br/>
  <a href="/" class="button">홈으로 가기 (빨리)</a>
</body>
</html>
