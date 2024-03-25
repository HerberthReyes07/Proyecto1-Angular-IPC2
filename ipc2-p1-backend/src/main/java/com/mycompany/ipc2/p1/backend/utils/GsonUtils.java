/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author herberthreyes
 */
public class GsonUtils<T> {

    private final Gson gson;

    public GsonUtils() {
        gson = new Gson();
    }

    public void sendAsJson(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");

        String res = gson.toJson(object);

        PrintWriter pw = response.getWriter();

        pw.print(res);
        pw.close();
    }

    public T readFromJson(HttpServletRequest request, Class<T> classT) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) buffer.append(line);

        String payload = buffer.toString();

        return gson.fromJson(payload, classT);
    }
}
