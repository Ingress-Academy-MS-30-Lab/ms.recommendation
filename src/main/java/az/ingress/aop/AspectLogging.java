package az.ingress.aop;

import az.ingress.logger.ApplicationLogger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AspectLogging {

    private final ApplicationLogger log = ApplicationLogger.getLogger(AspectLogging.class);

    @SneakyThrows
    @Around(value = "@within(az.ingress.aop.Log)")
    public Object logging(ProceedingJoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        log.info("ActionLog." + methodName + ".start - {}", joinPoint.getArgs());
        var result = joinPoint.proceed();
        log.info("ActionLog." + methodName + ".end.success - {}", joinPoint.getArgs());
        return result;
    }
}
