package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;

    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User newUser = new User(req.getParameter("name"),
                req.getParameter("psw"));
        newUser.setRoles(Collections.singleton(Role.of("USER")));
        try {
            User user = userService.create(newUser);

            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getUserId());

            Cookie cookie = new Cookie("Mate", user.getToken());
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/shop");
        } catch (DataProcessingException e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp").forward(req, resp);
        }
    }
}
