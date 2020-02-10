package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;
    private static Logger logger = Logger.getLogger(InjectDataController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User admin = new User("ADMIN", "111");
        admin.setRoles(Collections.singleton(Role.of("ADMIN")));
        try {
            userService.create(admin);
            resp.sendRedirect(req.getContextPath() + "/shop");
        } catch (DataProcessingException e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp").forward(req, resp);
        }
    }
}
