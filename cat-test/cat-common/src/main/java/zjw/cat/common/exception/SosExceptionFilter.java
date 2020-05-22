package zjw.cat.common.exception;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

/**
 * <p>
 * 自定义dubbo过滤异常类
 * 生产者配置即可
 * </p>
 *
 * @author clive
 * @since 2020-03-12 14:50
 */
@Activate(group = Constants.PROVIDER)
public class SosExceptionFilter implements Filter {
    private final Logger logger;

    public SosExceptionFilter() {
        this(LoggerFactory.getLogger(com.alibaba.dubbo.rpc.filter.ExceptionFilter.class));
    }

    public SosExceptionFilter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();
                    if (!(exception instanceof RuntimeException) && exception instanceof Exception) {
                        return result;
                    } else {
                        try {
                            Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                            Class<?>[] exceptionClassses = method.getExceptionTypes();
                            Class[] arr$ = exceptionClassses;
                            int len$ = exceptionClassses.length;

                            for (int i$ = 0; i$ < len$; ++i$) {
                                Class<?> exceptionClass = arr$[i$];
                                if (exception.getClass().equals(exceptionClass)) {
                                    return result;
                                }
                            }
                        } catch (NoSuchMethodException var11) {
                            this.logger.error("method: invoke NoSuchMethodException {}", var11);
                            return result;
                        }

                        this.logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);
                        String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                        String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                        exception.getLocalizedMessage();
                        if (serviceFile != null && exceptionFile != null && !serviceFile.equals(exceptionFile)) {
                            String className = exception.getClass().getName();
                            //自定义异常直接返回
                            if (className.startsWith("zjw.cat.common.exception.")) {
                                return result;
                            }
                            if (!className.startsWith("java.") && !className.startsWith("javax.")) {
                                return (Result) (exception instanceof RpcException ? result : new RpcResult(new RuntimeException(StringUtils.toString(exception))));
                            } else {
                                return result;
                            }
                        } else {
                            return result;
                        }
                    }
                } catch (Exception var12) {
                    this.logger.warn("Fail to MyExceptionFilter when called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + var12.getClass().getName() + ": " + var12.getMessage(), var12);
                    return result;
                }
            } else {
                return result;
            }
        } catch (RuntimeException var13) {
            this.logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + var13.getClass().getName() + ": " + var13.getMessage(), var13);
            throw var13;
        }
    }
}
