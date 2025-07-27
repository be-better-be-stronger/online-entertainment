package com.poly.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("view", "/WEB-INF/views/edit-profile.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
}

