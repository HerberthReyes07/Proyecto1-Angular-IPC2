/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.utils;

import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.TypeUser;

/**
 *
 * @author herberthreyes
 */
public class GeneralUtils {

    public GeneralUtils() {
    }

    public TypeUser getTypeUser(int type) {
        switch (type) {
            case 1:
                return TypeUser.ADMINISTRATOR;
            case 2:
                return TypeUser.OPERATOR;
            case 3:
                return TypeUser.RECEPTIONIST;
            default:
                System.out.println("TIPO NO ENCONTRADO");
        }
        return null;
    }
    
    public int getStatusPackageCode(PackageStatus ps) {
        switch (ps) {
            case EN_BODEGA:
                return 1;
            case EN_PUNTO_CONTROL:
                return 2;
            case EN_ESPERA_RETIRO:
                return 3;
            case RETIRADO:
                return 4;
            default:
                System.out.println("CODIGO NO ENCONTRADO");
        }
        return -1;
    }
}
