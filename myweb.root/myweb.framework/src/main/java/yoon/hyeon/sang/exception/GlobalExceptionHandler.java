package yoon.hyeon.sang.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
    Spring은 예외 발생 시, @ExceptionHandler를 다음 순서로 찾습니다:
    1. 가장 구체적인 예외 타입 (@ExceptionHandler(ArithmeticException.class))
    2. 조금 더 상위 예외 타입 (@ExceptionHandler(RuntimeException.class))
    3. 가장 상위 타입 (@ExceptionHandler(Exception.class))

    @ExceptionHandler({ IOException.class, SQLException.class }) 처럼 여러 예외를 한번에 처리할 수 도 있다
    */

    private static final Logger logger = LogManager.getLogger("externalApiCall");

    @ExceptionHandler(UserException.NotImplementException.class)
    public void handleNotImplementException(UserException.NotImplementException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        String message = ex.getMessage();
        message = message.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        String script = "<script>alert(\"" + message + "\");</script>";
        response.getWriter().write(script);
        response.getWriter().flush();
    }

    @ExceptionHandler(ApiException.InvalidContentType.class)
    public void handleInvalidContentType(ApiException.InvalidContentType ex, HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.error("[InvalidContentType 예외 발생]");
        logger.error("요청 URL: {}", request.getRequestURL());
        logger.error("예외 메시지: {}", ex.getMessage());
        logger.error("전체 스택 트레이스:", ex);

        response.setContentType("text/html; charset=UTF-8");

        String message = ex.getMessage();
        message = message.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        String script = "<script>alert(\"" + message + "\");</script>";
        response.getWriter().write(script);
        response.getWriter().flush();
    }

    // 사용자 커스텀 예외 처리
    @ExceptionHandler(UserException.class)
    public void handleCustomException(UserException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        String message = ex.getMessage();
        message = message.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        String script = "<script>alert(\"" + message + "\");</script>";
        response.getWriter().write(script);
        response.getWriter().flush();
    }

    // 공통 예외처리
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        String message = ex.getMessage() != null ? ex.getMessage() : "알 수 없는 오류가 발생했습니다. hsyoon@covision.co.kr에 문의하세요";
        String url = request.getRequestURL().toString();

        // 스택 트레이스 전체를 문자열로 캡처
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String fullTrace = sw.toString();

        // URL 인코딩 처리
        String encodedMessage = URLEncoder.encode(message, "UTF-8");
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        String encodedTrace = URLEncoder.encode(fullTrace, "UTF-8");

        // 컨트롤러 경로 호출로 변경
        StringBuilder script = new StringBuilder();
        script.append("<script>")
                .append("const popupUrl = '/dev/errorPopup?msg=")
                .append(encodedMessage)
                .append("&url=")
                .append(encodedUrl)
                .append("&trace=")
                .append(encodedTrace)
                .append("';")
                .append("window.open(popupUrl, 'errorPopup', 'resizable=yes,scrollbars=yes,width=1000,height=800');")
                .append("</script>");

        response.getWriter().write(script.toString());
    }

    // Unchecked(런타임)예외만 처리
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(UserException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        String message = ex.getMessage();
        message = message.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        String script = "<script>alert(\"" + message + "\");</script>";
        response.getWriter().write(script);
        response.getWriter().flush();
    }
}
