/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import java.sql.Connection;

/**
 *
 * @author herberthreyes
 */
public class ProcessDetailDB {

    private Connection connection = ConnectionDB.getConnection();

    public ProcessDetailDB() {
    }
    
    public void create(ProcessDetail processDetail){
    
    }

}
