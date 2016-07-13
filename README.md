# JFlask
JFlask is light-weight Java Web Framework inspired by *Python Flask*

### 开发进度
- [X] MVC 的 Web 架构
- [X] IOC 支持, 实现依赖注入
- [X] URL 重写，参数注入
- [X] Restful 接口支持
- [ ] 文件上传支持
- [ ] ORM 支持, 整合 Ebean, 实现 JPA 规范
- [ ] AOP 支持

### Demo

```java
package com.rayn.jflask.sample.controllers;

import com.rayn.jflask.framework.core.annotation.Controller;
import com.rayn.jflask.framework.core.annotation.Route;
import com.rayn.jflask.framework.mvc.model.JSONResult;
import com.rayn.jflask.framework.mvc.model.JSPView;
import com.rayn.jflask.framework.mvc.model.Params;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

    @Route("/")
    @Route("/index")
    public JSPView index() {
        return new JSPView("index.jsp")
                .put("greeting", "JFlask");
    }

    @Route("/form")
    public JSPView login() {
        return new JSPView("form.jsp");
    }

    @Route(value = "/form", method = { "POST" })
    public JSONResult toLogin(Params params) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", params.getString("username"));
        data.put("password", params.getString("password"));
        return new JSONResult(JSONResult.OK, "成功").setData(data);
    }
}

```