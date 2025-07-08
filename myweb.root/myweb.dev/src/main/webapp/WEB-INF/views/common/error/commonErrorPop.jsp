<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>오류 발생</title>
    <style>
        /* 전체 body를 꽉 채우고 패딩 최소화 */
        body, html {
            margin: 0; padding: 10px;
            width: 100vw; height: 100vh;
            font-family: "맑은 고딕", sans-serif;
            background-color: #fffaf8;
            box-sizing: border-box;
            overflow: hidden;
        }

        /* 팝업 컨테이너는 화면 꽉 채우기 */
        .popup-container {
            width: 100%;
            height: 100%;
            background-color: #fff;
            color: #333;
            display: flex;
            flex-direction: column;
            padding: 10px 15px;
            box-sizing: border-box;
            border: 1px solid #f5c6cb;
            border-radius: 6px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
        }

        .popup-title {
            font-size: 20px;
            color: #dc3545;
            font-weight: bold;
            margin-bottom: 15px;
            flex-shrink: 0;
        }

        /* 내용 영역은 flex로 세로 꽉 차게, 스크롤 가능하게 */
        .popup-content {
            flex: 1 1 auto;
            overflow-y: auto;
        }

        /* 요청 URL, 에러 메시지 스타일 */
        .popup-content strong {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
        }

        /* StackTrace는 monospace, 줄바꿈 없이 가로 스크롤 */
        .popup-trace {
            font-family: "D2Coding", "Consolas", "Courier New", monospace;
            background: #f1f1f1;
            border: 1px solid #ccc;
            padding: 10px;
            font-family: monospace;
            font-size: 13px;
            white-space: pre;  /* 줄바꿈 유지, 자동 줄바꿈 금지 */
            overflow-x: auto;
            overflow-y: auto;
            margin-top: 10px;
        }

        .popup-trace pre {
            margin: 0;
            padding: 10px;
            background: #f1f1f1;
            font-family: monospace;
            font-size: 13px;
            white-space: pre-wrap; /* 줄바꿈 유지, 너비 넘어가면 줄바꿈 */
            overflow-x: auto; /* 가로 스크롤 */
        }

        /* 닫기 버튼은 하단에 고정 */
        .btn-close {
            flex-shrink: 0;
            margin-top: 15px;
            padding: 12px 0;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 15px;
            cursor: pointer;
            text-align: center;
            width: 100%;
            user-select: none;
        }

        .btn-close:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="popup-container">
    <div class="popup-title">⚠ 오류가 발생했습니다</div>

    <div class="popup-content">
        <strong>요청 URL:</strong>
        <div>${url}</div>

        <strong>에러 메시지:</strong>
        <div>${errorMessage}</div>

        <strong>StackTrace:</strong>
        <div class="popup-trace">
<pre>
<c:forEach var="line" items="${stackTrace}">
${line}
</c:forEach>
</pre>
        </div>
    </div>

    <button class="btn-close" onclick="window.close()">닫기</button>
</div>

<script>
    // 팝업 크기 자동 조절 (최대 높이 600px, 너비는 내용에 맞게)
    window.onload = function () {
        const padding = 60;
        const width = document.body.scrollWidth + padding;
        const height = document.body.scrollHeight + padding;
        window.resizeTo(width, height);
    };
</script>
</body>
</html>
