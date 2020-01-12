package internetshop.controller;

import static internetshop.controller.DeleteBucketController.USER_ID;

import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Order;
import internetshop.service.BucketService;
import internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserOrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String bucketId = req.getParameter("id");
        Bucket bucket = bucketService.getByBucketId(Long.valueOf(bucketId));
        Order order = orderService.completeOrder(bucket.getItems(), USER_ID);

        bucketService.delete(bucket);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/userOrder.jsp").forward(req, resp);
    }
}
