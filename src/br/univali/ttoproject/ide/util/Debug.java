package br.univali.ttoproject.ide.util;

import javax.swing.*;

public class Debug {

    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message, "Debug", JOptionPane.ERROR_MESSAGE);
    }

    public static void var(String name, Object var) {
        System.out.println("DEBUG - " + name + ": [" + var + "]");
    }

    public static void print(Object message) {
        System.out.println("DEBUG - Message: " + message);
    }

    public static void print(Object message, char end) {
        System.out.print("DEBUG - Message: " + message + end);
    }

}
