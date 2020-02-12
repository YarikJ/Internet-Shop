package internetshop.controller;

import internetshop.exceptions.AuthorizationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LogInController extends HttpServlet {
    @Inject
    private static UserService userService;

    private static final Logger LOGGER = Logger.getLogger(LogInController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/logIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("psw");
        try {
            User user = userService.login(name, pass);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getUserId());

            Cookie cookie = new Cookie("Mate", user.getToken());
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/shop");
        } catch (DataProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp")
                    .forward(req, resp);
        } catch (AuthorizationException e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("errorMsg", "incorrect name or password");
            req.getRequestDispatcher("/WEB-INF/views/logIn.jsp").forward(req, resp);
        }
    }
}
