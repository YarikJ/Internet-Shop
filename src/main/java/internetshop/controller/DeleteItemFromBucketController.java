package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteItemFromBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        Long userId = (Long) req.getSession().getAttribute("userId");

        bucketService.deleteItem(bucketService.getByUserId(userId),
                itemService.get(Long.valueOf(itemId)));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
