/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package private_school;

import java.util.Scanner;
import static models.Procedures.insertOrSelect;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class PrivateSchoolDataBase {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        insertOrSelect();
    }

}
