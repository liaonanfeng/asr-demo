package com.ewt360.asr.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ewt360.asr.common.constant.APIConstant;
import com.ewt360.asr.common.exception.ServiceException;
import com.ewt360.asr.common.utils.JsonUtils;
import com.ewt360.asr.facade.base.enumeration.BaseResultCodeEnum;
import com.ewt360.asr.facade.base.result.BaseResult;
import com.ewt360.asr.facade.base.result.QueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Spring MVC 配置
 *
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 统一异常处理
     *
     * @param exceptionResolvers 异常处理器列表
     */
    @Override
    public void configureHandlerExceptionResolvers(
        List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @SuppressWarnings("unchecked")
            @Override
            public ModelAndView resolveException(HttpServletRequest request,
                                                 HttpServletResponse response, Object handler, Exception e) {
                log.warn("-->-->configureHandlerExceptionResolvers:",e);
                log.error("[ErrorCode:{}]resolveException:method:{},msg:{},param:{}",BaseResultCodeEnum.INTERFACE_SYSTEM_ERROR, request.getMethod(), e.getMessage(),JsonUtils.toJson(request.getParameterMap()));
                e.printStackTrace();
                QueryResult result = new QueryResult(false);
                if (e instanceof ServiceException) {
                    result.setCode(BaseResultCodeEnum.ILLEGAL_OPERATION.getCode());
                    result.setMessage(e.getMessage());
                    log.info("业务执行异常_" + e.getMessage(), e);
                } else if (e instanceof NoHandlerFoundException) {
                    result.setCode(BaseResultCodeEnum.SYSTEM_FAILURE.getCode());
                    result.setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException || e instanceof IllegalArgumentException || e instanceof JSONException) {
                    result.setCode(BaseResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
                    result.setMessage(e.getMessage());
                } else if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
                    Map<String,String> fieldErrors = getFieldErrors(e);
                    result.setCode(BaseResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
                    result.setMessage(
                            JsonUtils.toJson(fieldErrors));
                    // 文件上传异常信息
                } else if (e instanceof MultipartException){
                    result.setCode(BaseResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
                    result.setMessage(e.getCause().getMessage());
                } else if (e instanceof ResourceAccessException || e instanceof RestClientException){
                    result.setCode(BaseResultCodeEnum.INTERFACE_SYSTEM_ERROR.getCode());
                    result.setMessage(e.getMessage());
                }else {
                    result.setCode(BaseResultCodeEnum.SYSTEM_FAILURE.getCode());
                    result.setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请检查");
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                    log.info(message, e);
                }
                responseResult(response, result);
                return new ModelAndView();
            }
        });
    }

//    /**
//     * 解决跨域问题,如果需要可以将注释打开
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        log.info("-->addCorsMappings");
//         registry.addMapping("/**")
//             .allowedOrigins("*")
//             .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
//                 .allowCredentials(true);
//         super.addCorsMappings(registry);
//    }

    /**
     * 不加以下代码会出现swagger-ui.html 404
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private Map<String, String> getFieldErrors(Exception e) {
        List<FieldError> fieldErrors =
            e instanceof BindException ? ((BindException) e).getFieldErrors()
                : ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        // 支持在资源文件中定义错误信息，错误信息的key作为验证注解的message属性值，用大括号括起来
        return fieldErrors.stream().collect(Collectors.toMap(fieldError -> fieldError.getField(),
            fieldError -> fieldError.getDefaultMessage()));
    }

    private void responseResult(HttpServletResponse response, BaseResult result) {
        response.setCharacterEncoding(APIConstant.CHARSET_UTF8);
        response.setHeader(APIConstant.HTTP_CONTENT_TYPE_KEY, MediaType.APPLICATION_JSON_UTF8_VALUE.intern());
        response.setStatus(HttpStatus.OK.value());
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(JSON.toJSONString(result));

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }finally {
            if (null != pw){
                pw.flush();
                pw.close(); //关闭流
            }
        }
    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
//    }
}
