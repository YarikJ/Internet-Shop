package internetshop.controller;

import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllItemsController extends HttpServlet {
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/allItems.jsp").forward(req, resp);

    }
}