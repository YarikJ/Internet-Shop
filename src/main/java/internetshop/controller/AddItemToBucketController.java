package internetshop.controller;

import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.service.BucketService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;

    private static final Logger LOGGER = Logger.getLogger(AddItemToBucketController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            bucketService.addItem(userId,
                    Long.valueOf(req.getParameter("item_id")));
            req.getRequestDispatcher("allItems").forward(req, resp);
        } catch (DataProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("msg", e);
            req.getRequestDispatcher("/WEB-INF/views/exceptionOccur.jsp").forward(req, resp);
        }
    }
}

