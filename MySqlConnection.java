/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testConnection;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Burhan Shaheen
 * @IDE_version NetBeans IDE 8.2
 * @regNo 4011-FBAS/BSCS/F18("B")
 * @subject Advance Computer Programming
 * @teacher Sir Majid Bashir
 * @assignment X3 JDBC Connectivity
 * @completed I have Completed this Project Alone.
 
 */
public class MySqlConnection {

    public static String fetchDate(String type) {
        Connection conn = connect();
        String sql = "select * from " + type;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String temp = new String();
            for (int i = 1; i <= col; i++) {
                temp += rsmd.getColumnName(i);
                if (i != col) {
                    temp += "     :     ";
                }
            }
            temp += "\n\n";
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    temp += rs.getString(i);
                    if (i != col) {
                        temp += "     :     ";
                    }

                }
                temp += "\n\n";
            }

            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String BedsAvailability() {
        JFrame f = new JFrame();
        Connection conn = connect();
        String sql = "select * from beds_t";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            String temp = new String();
            for (int i = 1; i <= col; i++) {
                temp += rsmd.getColumnName(i);
                if (i != col) {
                    temp += "     :     ";
                }
            }
            temp += "\n\n";
            while (rs.next()) {
                if (rs.getString(3).equals("UNAVAILABLE")) {
                    for (int i = 1; i <= col; i++) {

                        temp += rs.getString(i);
                        if (i != col) {
                            temp += "     :     ";
                        }
                    }
                    temp += "\n\n";
                }

            }
            return temp;
        } catch (Exception ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String DoctorTreatment() {
        JFrame f = new JFrame();
        Connection conn = connect();
        String sql = "select * from patient_t";
        String temp;
        try {

            Statement st = conn.createStatement();

            do {
                ResultSet rs = st.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                temp = new String();
                int col = rsmd.getColumnCount();
                for (int i = 1; i <= col; i++) {
                    temp += rsmd.getColumnName(i);
                    if (i != col) {
                        temp += "     :     ";
                    }
                }
                temp += "\n\n";
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Patient \"Id\" for Which You want to Get Doctor Name "));
                int i = 1;
                boolean flag = true;
                while (i <= rsmd.getColumnCount()) {
                    while (rs.next()) {
                        if (id == Integer.parseInt(rs.getString(1))) {
                            flag = false;
                            for (int j = 1; j <= col; j++) {
                                temp += rs.getString(j);
                                if (j != col) {
                                    temp += "     :     ";
                                }
                            }
                            temp += "\n\n";
                        }

//                        return temp;
                    }
                    if (!flag) {
                        break;
                    }
                    i++;
                }
               if (!flag) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(f, "Patient ID not Available, Please Enter Valid Patient ID", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            } while (true);

            return temp;
        } catch (Exception ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagementsystem", "root", "");
        } catch (ClassNotFoundException ex) {
            System.out.println("Exception " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQL Exception " + ex.getMessage());
        }
        return con;
    }

    public static String SubMenu() {
        return "++++++++++++++++++ MAIN MENU ++++++++++++++++++\n\n"
                + "1 ) PATIENT TABLE!!!\n\n"
                + "2 ) DOCTOR TABLE !!!\n\n"
                + "3 ) ROOM TABLE!!!\n\n"
                + "4 ) BED TABLE!!!\n\n"
                + "5 ) GO BACK!!!\n";
    }

    public static String Menu() {
        return "++++++++++++++++++ MAIN MENU ++++++++++++++++++\n\n"
                + "1 ) Retrives details of all tables!!!\n\n"
                + "2 ) Retrives details of the doctors treating a particular patient !!!\n\n"
                + "3 ) Retrives bed_numbers of all beds that are currently occupied(Status =UnAvailable)!!!\n\n"
                + "4 ) Exit!!!\n";
    }

    public static void SubResult() {
        JFrame f = new JFrame();
        do {
            int ch = Integer.parseInt(JOptionPane.showInputDialog(SubMenu()));
            switch (ch) {
                case 1:

                    String patient = MySqlConnection.fetchDate("patient_t");
                    JOptionPane.showMessageDialog(f, patient, "PATIENT TABLE", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    String doctor = MySqlConnection.fetchDate("doctor_t");
                    JOptionPane.showMessageDialog(f, doctor, "DOCTOR TABLE", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    String room = MySqlConnection.fetchDate("rooms_t");
                    JOptionPane.showMessageDialog(f, room, "ROOM TABLE", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4:
                    String bed = MySqlConnection.fetchDate("beds_t");
                    JOptionPane.showMessageDialog(f, bed, "BED TABLE", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 5:
                    break;
                default:
                    JOptionPane.showMessageDialog(f, "!WRONG CHOICE", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
            if (ch == 5) {
                break;
            }
        } while (true);
    }

    public static void main(String orgs[]) {
        JFrame f = new JFrame();

        do {
            int choice = Integer.parseInt(JOptionPane.showInputDialog(Menu()));
            switch (choice) {
                case 1:
                    SubResult();
                    break;
                case 2:
                    String doctor = MySqlConnection.DoctorTreatment();
                    JOptionPane.showMessageDialog(f, doctor, "DOCTOR TREATED", JOptionPane.INFORMATION_MESSAGE);

                    break;
                case 3:
                    String bed = MySqlConnection.BedsAvailability();
                    JOptionPane.showMessageDialog(f, bed, "UNAVAILABLE BEDS", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(f, "!WRONG CHOICE", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
        } while (true);
    }
}
