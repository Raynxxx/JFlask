# JFlask
JFlask is light-weight Java Web Framework inspired by *Python Flask*

### 开发进度
- [X] 基于Servlet 3.0 的 MVC 的 Web 架构
- [X] 实现注解完成 URL 配置，自动参数注入
- [X] Restful 风格接口支持
- [X] 实现注解驱动的依赖注入
- [ ] 文件上传支持
- [ ] ORM 支持 ~~整合 Ebean, 实现 JPA 规范~~
- [ ] AOP 支持
- [ ] 更多......

### Demo

```java
import com.rayn.jflask.framework.annotation.ioc.AutoWired;
import com.rayn.jflask.framework.annotation.web.*;
import com.rayn.jflask.framework.mvc.Respond;
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
        return Respond.text("Hello JFlask");
    }

    @Route("/<text>")
    public Result echo(String text) {
        return Respond.jsp("index.jsp",
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
        Map<String, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("user", user.getUsername());
        data.put("password", user.getPassword());
        return Respond.json(data);
    }
}
```