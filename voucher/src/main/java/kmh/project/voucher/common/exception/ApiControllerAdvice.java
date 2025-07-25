package kmh.project.voucher.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;



@RestControllerAdvice
public class ApiControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e){
        return createErrorResponse.apply(e, Level.INFO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handleIllegalStateExceptionException(IllegalStateException e){
        return createErrorResponse.apply(e, Level.INFO);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleIllegalStateException(Exception e){
        return createErrorResponse.apply(e, Level.ERROR);
    }

/*    private static ErrorResponse createErrorResponse(final String e) {
        return new ErrorResponse(e, LocalDateTime.now(), UUID.randomUUID());
    }
    */

    private final BiFunction<Exception, Level, ErrorResponse> createErrorResponse = (e, level) -> {
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), UUID.randomUUID());
        switch (level) {
            case ERROR:
                log.error("### traceId: {}", errorResponse.traceId(), e);
                break;
            case WARN:
                log.warn("### traceId: {}", errorResponse.traceId(), e);
                break;
            case INFO:
                log.info("### traceId: {}", errorResponse.traceId(), e);
                break;
            case DEBUG:
                log.debug("### traceId: {}", errorResponse.traceId(), e);
                break;
            case TRACE:
                log.trace("### traceId: {}", errorResponse.traceId(), e);
                break;
        }

        return errorResponse;
    };
}
