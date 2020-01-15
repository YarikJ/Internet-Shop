package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Bucket bucket = bucketService.getByUserId(userId);
        req.setAttribute("bucket", bucket);
        if (req.getParameter("item_id") == null) {
            req.setAttribute("msg", "Bucket is empty");
            req.getRequestDispatcher("/WEB-INF/views/addBucket.jsp").forward(req, resp);
            return;
        }

        bucketService.addItem(bucket.getIdBucket(),
                Long.valueOf(req.getParameter("item_id")));

        req.getRequestDispatcher("/WEB-INF/views/addBucket.jsp").forward(req, resp);
    }
}

