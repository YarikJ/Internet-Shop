package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.service.BucketService;
import internetshop.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;
    static final Long USER_ID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");

        bucketService.deleteItem(bucketService.getByUserId(USER_ID), itemService.get(Long.valueOf(itemId)));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
