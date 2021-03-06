package internetshop.filters;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.User;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    @Inject
    private static UserService userService;
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getCookies() == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("Mate")) {
                try {
                    Optional<User> user = userService.getByToken(cookie.getValue());
                    if (user.isPresent()) {
                        LOGGER.info("User " + user.get().getName() + "was authenticated");
                        chain.doFilter(req, resp);
                        return;
                    }
                } catch (DataProcessingException e) {
                    LOGGER.error(e.getMessage(), e);
                    req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp")
                            .forward(req, resp);
                }
            }
        }

        LOGGER.info("user was not authenticated");
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {
    }
}
