package az.ingress.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectLogging {

    @SneakyThrows
    @Around(value = "@annotation(ToLog)")
    public Object logging(ProceedingJoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        log.info("ActionLog." + methodName + ".start - {}", joinPoint.getArgs());
        var result = joinPoint.proceed();
        log.info("ActionLog." + methodName + ".end.success - {}", joinPoint.getArgs());
        return result;
    }
}
