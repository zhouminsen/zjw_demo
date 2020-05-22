package zjw.cat.consumer.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import zjw.cat.consumer.annotation.LogAnnotation;
import zjw.cat.producer.entity.SysLog;
import zjw.cat.producer.service.SysLogService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * <p>
 * 日志拦截器
 * </p>
 *
 * @author clive
 * @since 2019-06-24
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {
    /**
     * 本地IP
     */
    public static final String LOCALHOST_IP = "127.0.0.1"; //NOSONAR


    private SysLog sysLog = null;

    @Pointcut("@annotation(zjw.cat.consumer.annotation.LogAnnotation)")
    public void webLog() {
        //do nothing
    }

    @Reference(timeout = 10000, retries = 0)
    protected SysLogService logService;

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long l = System.currentTimeMillis();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) attributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        sysLog = new SysLog();
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLog.setHttpMethod(request.getMethod());
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile) {
                args[i] = o.toString();
            }
        }
        String str = JSONObject.toJSONString(args);
        String ip = LOCALHOST_IP;
        sysLog.setRemoteAddr(ip);
        sysLog.setRequestUri(request.getRequestURL().toString());
        if (session != null) {
            sysLog.setSessionId(session.getId());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation mylog = method.getAnnotation(LogAnnotation.class);
        if (mylog != null) {
            //注解上的描述
            sysLog.setTitle(mylog.value());
        }
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        sysLog.setUseTime(System.currentTimeMillis() - l);
        logService.save(sysLog);
        return obj;
    }
}
