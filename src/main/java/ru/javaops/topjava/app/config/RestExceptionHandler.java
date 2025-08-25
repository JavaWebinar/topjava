package ru.javaops.topjava.app.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.javaops.topjava.common.error.AppException;
import ru.javaops.topjava.common.error.ErrorType;

import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static ru.javaops.topjava.common.error.ErrorType.*;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class RestExceptionHandler {
    public static final String ERR_PFX = "ERR# ";

    @Getter
    private final MessageSource messageSource;

    //    https://stackoverflow.com/a/52254601/548473
    static final Map<Class<? extends Throwable>, ErrorType> HTTP_STATUS_MAP = new LinkedHashMap<>() {
        {
// more specific first
            put(NoResourceFoundException.class, NOT_FOUND);
            put(AuthenticationException.class, UNAUTHORIZED);
            put(FileNotFoundException.class, NOT_FOUND);
            put(NoHandlerFoundException.class, NOT_FOUND);
            put(UnsupportedOperationException.class, APP_ERROR);
            put(EntityNotFoundException.class, DATA_CONFLICT);
            put(DataIntegrityViolationException.class, DATA_CONFLICT);
            put(IllegalArgumentException.class, BAD_DATA);
            put(ValidationException.class, BAD_REQUEST);
            put(HttpRequestMethodNotSupportedException.class, BAD_REQUEST);
            put(ServletRequestBindingException.class, BAD_REQUEST);
            put(RequestRejectedException.class, BAD_REQUEST);
            put(AccessDeniedException.class, FORBIDDEN);
        }
    };

    @ExceptionHandler(BindException.class)
    ProblemDetail bindException(BindException ex, HttpServletRequest request) {
        Map<String, String> invalidParams = getErrorMap(ex.getBindingResult());
        String path = request.getRequestURI();
        log.warn(ERR_PFX + "BindException with invalidParams {} at request {}", invalidParams, path);
        return createProblemDetail(ex, path, BAD_REQUEST, "BindException", Map.of("invalid_params", invalidParams));
    }

    private Map<String, String> getErrorMap(BindingResult result) {
        Map<String, String> invalidParams = new LinkedHashMap<>();
        for (ObjectError error : result.getGlobalErrors()) {
            invalidParams.put(error.getObjectName(), getErrorMessage(error));
        }
        for (FieldError error : result.getFieldErrors()) {
            invalidParams.put(error.getField(), getErrorMessage(error));
        }
        return invalidParams;
    }

    private String getErrorMessage(ObjectError error) {
        return error.getCode() == null ? error.getDefaultMessage() :
                messageSource.getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage(), LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail exception(Exception ex, HttpServletRequest request) {
        return processException(ex, request, Map.of());
    }

    ProblemDetail processException(@NonNull Throwable ex, HttpServletRequest request, Map<String, Object> additionalParams) {
        Optional<ErrorType> optType = findErrorType(ex);
        Throwable root = getRootCause(ex);
        if (optType.isEmpty() && root != ex) {
            ex = root;
            optType = findErrorType(root);
        }
        String path = request.getRequestURI();
        if (optType.isPresent()) {
            log.error(ERR_PFX + "Exception {} at request {}", ex, path);
            return createProblemDetail(ex, path, optType.get(), ex.getMessage(), additionalParams);
        } else {
            log.error(ERR_PFX + "Exception " + root + " at request " + path, root);
            return createProblemDetail(root, path, APP_ERROR, "Exception " + root.getClass().getSimpleName(), additionalParams);
        }
    }

    private Optional<ErrorType> findErrorType(Throwable ex) {
        if (ex instanceof AppException aex) {
            return Optional.of(aex.getErrorType());
        }
        Class<? extends Throwable> exClass = ex.getClass();
        return HTTP_STATUS_MAP.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(exClass))
                .findAny().map(Map.Entry::getValue);
    }

    //    https://datatracker.ietf.org/doc/html/rfc7807
    private ProblemDetail createProblemDetail(Throwable ex, String path, ErrorType type, String defaultDetail, @NonNull Map<String, Object> additionalParams) {
        ErrorResponse.Builder builder = ErrorResponse.builder(ex, type.status, defaultDetail);
        ProblemDetail pd = builder
                .title(type.title).instance(URI.create(path))
                .build().updateAndGetBody(messageSource, LocaleContextHolder.getLocale());
        additionalParams.forEach(pd::setProperty);
        return pd;
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    private static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
