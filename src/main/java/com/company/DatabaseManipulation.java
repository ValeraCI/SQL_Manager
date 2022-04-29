package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManipulation {
    private final String anyNameOfTables = "AnyTable";
    Input input = new Input();
    private Connection connection;
    private Statement statement;

    public DatabaseManipulation(String username, String password, String URL, String driver){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean exists(String name){
        List<String> tables = getAllTables();
        for(String s: tables){
            if(s.equals(name)){
                return true;
            }
        }
        return false;
    }

    private String generateString(String name){
        String ret = "CREATE table " + name + "(id SERIAL";
        System.out.print("Укажите количество столбцов таблицы: ");
        int st = input.intIn();
        boolean work;
        for (int i = 0; i<st; i++){
            work = true;
            System.out.print("Укажите название столбца: ");
            name = input.strIn();
            while (work){
                System.out.println("Укажите тип столбца:\n1.Целочисленные значения(int)\n2.Приблизительные числа(float)\n" +
                        "3.Символьные строки(varchar[120])");
                work = false;
                switch(input.intIn()){
                    case 1 -> ret += ", " + name + " int not null";
                    case 2 ->ret += ", " + name + " float not null";
                    case 3 ->ret += ", " + name + " varchar[120] not null";
                    default -> {
                        work = true;
                        System.out.println("Неверный индекс");
                    }
                }
            }

        }
        ret += ", PRIMARY KEY(ID))";
        return ret;
    }

    public void deleteTable(String name){
        try {
            statement.executeUpdate("DROP table " + name);
            statement.executeUpdate("DELETE FROM " + anyNameOfTables + " where name = '{" + name + "}'");
            System.out.println("Таблица " +  name + " успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createNewTable(String name) {
        if(exists(name)){
            System.out.println("Таблица с таким именем уже существует");
            return;
        }
        try {
            statement.executeUpdate(generateString(name));
            statement.executeUpdate("INSERT INTO " + anyNameOfTables + "(name) values ('{" + name + "}')");
            System.out.println("Таблица " + name + " создана успешно");
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public  List<String> getAllTables(){
        List<String> list = new ArrayList();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * from " + anyNameOfTables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                if (!resultSet.next()) break;
                list.add(resultSet.getString("name").replaceAll("[\\{\\}]", ""));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

     public void allTables(){
         List list = getAllTables();
         for(int i = 0; i < list.size(); i++){
             System.out.println((i+1) + ". " + list.get(i));
         }
    }


}
