package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Order;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GetAllUserOrdersController extends HttpServlet {
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    private static final Logger LOGGER = Logger.getLogger(ShowBucketController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            List<Order> orders = orderService.getUserOrders(userService.get(userId));
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/allUserOrders.jsp")
                    .forward(req, resp);
        } catch (DataProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp").forward(req, resp);
        }
    }
}
