/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.Customer;
import com.mycompany.ipc2.p1.backend.service.ReceptionistService;
import com.mycompany.ipc2.p1.backend.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
@WebServlet("/customers/*")
public class CustomerController extends HttpServlet {

    private final GsonUtils<Customer> gsonCustomer;
    private final ReceptionistService receptionistService;

    public CustomerController() {
        gsonCustomer = new GsonUtils<>();
        receptionistService = new ReceptionistService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO GET / CUSTOMER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Customer> customersFromDB = receptionistService.getAllCustomers();
            response.setStatus(HttpServletResponse.SC_OK);
            gsonCustomer.sendAsJson(response, customersFromDB);

        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/" + splits[1])) {
                    String customerNit = splits[1];
                    try {
                        Integer.parseInt(customerNit);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    Customer customerFromDB = receptionistService.getCustomerByNit(customerNit);
                    if (customerFromDB == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                        gsonCustomer.sendAsJson(response, customerFromDB);
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                //return;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO POST / CUSTOMER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        Customer customerFromJson = gsonCustomer.readFromJson(request, Customer.class);
        System.out.println("Cliente enviado: " + customerFromJson.toString());

        receptionistService.createCustomer(customerFromJson);
        response.setStatus(HttpServletResponse.SC_OK);
        gsonCustomer.sendAsJson(response, customerFromJson);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
