package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User("Billie", "111");
        user.addRole(Role.of("USER"));
        userService.create(user);

        User admin = new User("Finneas", "333");
        admin.addRole(Role.of("ADMIN"));
        userService.create(admin);

        resp.sendRedirect(req.getContextPath() + "/shop");
    }
}
