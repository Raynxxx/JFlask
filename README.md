# JFlask
JFlask is light-weight Java Web Framework inspired by *Python Flask*

### 开发进度
- [X] 基于Servlet 3.0 的 MVC 的 Web 架构
- [X] 实现注解完成 URL 配置，自动参数注入
- [X] Restful 风格接口支持
- [X] 实现注解驱动的依赖注入
- [X] 文件上传支持
- [ ] ORM 支持 ~~整合 Ebean, 实现 JPA 规范~~
- [ ] AOP 支持
- [ ] 更多......

### Demo

```java
import com.rayn.jflask.framework.annotation.ioc.AutoWired;
import com.rayn.jflask.framework.annotation.web.*;
import com.rayn.jflask.framework.mvc.Respond;
import com.rayn.jflask.framework.mvc.WebContext;
import com.rayn.jflask.framework.mvc.model.Params;
import com.rayn.jflask.framework.mvc.result.Result;
import com.rayn.jflask.sample.models.User;
import com.rayn.jflask.sample.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ExampleController {

    @AutoWired
    private UserService userService;

    @Route("/")
    public Result index() {
        return Respond.html("index.html");
    }

    @Route("/login")
    public Result login() {
        String username = WebContext.Cookie.get("username");
        if (username != null) {
            System.out.println("has login: " + username);
            return Respond.redirect("/");
        }
        return Respond.jsp("login.jsp");
    }

    @Route(value = "/login", method = "POST")
    public Result checkLogin(Params params) {
        WebContext.Cookie.put("username", params.get("username"), 3600);
        return Respond.redirect("/");
    }

    @Route("/<text>")
    public Result echo(String text) {
        return Respond.jsp("echo.jsp",
                "greeting", "JFlask",
                "text", text);
    }

    @Route("/users")
    public Result users() {
        return Respond.jsp("users.jsp",
                "users", userService.findAll());
    }

    @Route(value = "/users", method = "POST")
    public Result createUser(Params params) {
        User user = params.toModel(User.class);
        // WebContext 提供上传文件的接口
        WebContext.uploadFile(params.getFile("avatar"));
        Map<String, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("user", user.getUsername());
        return Respond.json(data);
    }
}
```