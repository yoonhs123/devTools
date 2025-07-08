<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- commonScripts: 공통 JS/CSS 로드 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>

<style>
    /* 기본 스타일 */
    html, body {
        font-family: Arial, sans-serif;
        margin: 0; padding: 0;
        height: 100%;
        min-width: 100%;
        background-color: #f9f9f9;
    }

    body {
        display: inline-flex;
        flex-direction: column;
    }

    header, footer {
        width: 100%;
        padding: 20px;
        box-sizing: border-box;
    }

    #main-content {
        flex: 1;    /* header, footer를 제외한 나머지 영역 차지 */
        display: flex;
    }
</style>
