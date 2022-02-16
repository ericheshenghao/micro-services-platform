package cn.central.common.utils;


import cn.central.common.model.BasicResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author he
 * @date 2018/12/20
 */
public class ResponseUtil {

    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response
     * @param msg          返回信息
     * @param httpStatus   返回状态码
     * @throws IOException
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, int httpStatus) throws IOException {
        BasicResponse result = BasicResponse.of(null, httpStatus, msg);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response
     * @param obj
     */
    public static void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, Object obj) throws IOException {
        BasicResponse result = BasicResponse.succeed(obj);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     *
     * @param objectMapper
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void responseFailed(ObjectMapper objectMapper, HttpServletResponse response, String msg, Integer code) throws IOException {
        BasicResponse result = BasicResponse.failed(msg, code);
        responseWrite(objectMapper, response, result);
    }

    private static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, BasicResponse result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try (
                Writer writer = response.getWriter()
        ) {

            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        }
    }
}
