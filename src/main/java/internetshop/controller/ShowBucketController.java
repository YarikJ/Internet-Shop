package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    private static Logger logger = Logger.getLogger(ShowBucketController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            Bucket bucket = bucketService.getByUserId(userId);
            req.setAttribute("bucket", bucket);

            if (bucket.getItems().isEmpty()) {
                req.setAttribute("msg", "Bucket is empty");
            }
            req.getRequestDispatcher("/WEB-INF/views/bucket.jsp").forward(req, resp);
        } catch (DataProcessingException e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp").forward(req, resp);
        }
    }
}
