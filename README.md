# JFlask
JFlask is light-weight Java Web Framework inspired by *Python Flask*

### 开发进度
- [X] 基于Servlet 3.0 的 MVC 的 Web 架构
- [X] 实现注解完成 URL 配置，自动参数注入
- [X] Restful 风格接口支持
- [X] 实现注解驱动的依赖注入
- [X] 文件上传支持
- [ ] ActiveRecord 支持 ~~整合 Ebean, 实现 JPA 规范~~
- [ ] AOP 支持
- [ ] 更多......

### Demo


```java
// Controller
import ......

@Controller
public class ExampleController {

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
        System.out.println("find rayn: " + User.finder.select("id, username")
            .where("username = ?", "rayn").first());
        System.out.println("find first: " + User.finder.first());
        System.out.println("find last: " + User.finder.last());
        return Respond.jsp("users.jsp",
                "users", User.finder.findAll());
    }

    @Route(value = "/users", method = "POST")
    public Result createUser(Params params) {
        User user = params.toModel(User.class);
        // 文件保存
        MultipartFile file = params.getFile("avatar");
        if (file != null) {
            file.save("avatar/");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("user", user.getUsername());
        return Respond.json(data);
    }
}
```


```java
// Model
import ......

@Table(name = "user")
public class User extends BaseModel<User> {

    public static final LinqQuery<User> finder = new DefaultLinqQuery<>(User.class);

    @Column(name = "id", isPrimary = true)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // ......
}

```
