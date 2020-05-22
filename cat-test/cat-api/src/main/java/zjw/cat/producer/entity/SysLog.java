
package zjw.cat.producer.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String title;

    private String remoteAddr;

    private String username;

    private String requestUri;

    private String httpMethod;

    private String classMethod;

    private String params;

    private String sessionId;

    private String response;

    private Long useTime;

    private String browser;

    private String area;

    private String province;

    private String city;

    private String isp;

    private String exception;


}
