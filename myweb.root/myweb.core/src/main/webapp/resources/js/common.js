// 1. 공통 AJAX 설정
$.ajaxSetup({
    contentType: 'application/json; charset=UTF-8',
    dataType: 'text',
    async: true
});

// 1-1 공통 Ajax 콜백처리
$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    const origSuccess = options.success;
    const origError = options.error;

    options.success = function (response, status, xhr) {
        defaultAjaxSuccessHandler(response, status, xhr);  // ✅ 무조건 호출
        if (typeof origSuccess === "function") {
            origSuccess(response, status, xhr);  // 👈 기존 핸들러도 호출
        }
    };

    options.error = function (xhr, status, error) {
        defaultAjaxErrorHandler(xhr, status, error);  // ✅ 무조건 호출
        if (typeof origError === "function") {
            origError(xhr, status, error);  // 👈 기존 핸들러도 호출
        }
    };
});


// 1-2 공통 성공 처리 (예외처리되서 성공으로 반환된 값 처리를 위한 재정의)
function defaultAjaxSuccessHandler(response, status, xhr) {
    const contentType = xhr.getResponseHeader("Content-Type") || "";

    // 서버에서 <script> 응답 시 스크립트 실행
    if (contentType.includes("text/html") && /<script[\s\S]*?>[\s\S]*?<\/script>/gi.test(response)) {
        try {
            const container = document.createElement('div');
            container.innerHTML = response;
            const scripts = container.querySelectorAll('script');
            scripts.forEach(script => eval(script.innerText));
        } catch (e) {
            //예외처리의 예외가 발생했을경우
            handleExceptionFallback("fail to handle exception handling", location.href, e.stack || e.toString());
        }
    } else {
        // 일반적으로 성공하는 경우
        // 여기에 로직을 작성해서는 안된다
    }
}

// 1-3 공통 예외 처리
function defaultAjaxErrorHandler(xhr, status, error) {
    let message = "알 수 없는 오류가 발생했습니다";
    let url = location.href;
    let trace = "";

    if (xhr && xhr.responseText) {
        const contentType = xhr.getResponseHeader("Content-Type") || "";

        // 서버에서 <script> 응답 시 스크립트 실행
        if (contentType.includes("text/html") && /<script[\s\S]*?>[\s\S]*?<\/script>/gi.test(xhr.responseText)) {
            try {
                const container = document.createElement('div');
                container.innerHTML = xhr.responseText;
                const scripts = container.querySelectorAll('script');
                scripts.forEach(script => eval(script.innerText));
                return;
            } catch (e) {
                message = "오류 스크립트 실행 실패";
                trace = e.stack || e.toString();
            }
        } else {
            message = xhr.responseText;
            trace = `${status} ${error}\n\n${xhr.responseText}`;
        }
    }

    handleExceptionFallback(message, url, trace);
}

// 1-4 공통 예외정보 모달 처리
function handleExceptionFallback(message, url, trace) {
    const data = {
        msg: message,
        url: url,
        trace: trace
    };

    fetch('/core/errorPopup', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(html => {
            showModal(html); // 서버에서 받은 JSP 모달 HTML 표시
        })
        .catch(err => {
            console.error("예외 모달 표시 실패:", err);
            alert(message);  // 최후의 fallback
        });
}
