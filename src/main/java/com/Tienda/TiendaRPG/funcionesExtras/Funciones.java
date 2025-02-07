/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Tienda.TiendaRPG.funcionesExtras;

public class Funciones {
    /**
    * .length es para contar la cantidad de caracteres. Lo de abajo sirve para limitar el uso de caracteres 
    * en una contraseña 
    */
    public boolean verificarContrasena(String contrasena) {
        if (contrasena.length() > 16) {
            throw new IllegalArgumentException("El límite de caracteres permitidos es de 16");
        } else if (contrasena.length() < 8) {
            throw new IllegalArgumentException("El mínimo de caracteres permitidos es de 8");
        }
        return true;
    }
}

    
