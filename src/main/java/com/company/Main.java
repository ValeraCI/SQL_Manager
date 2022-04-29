package com.company;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        final String DRIVER = "org.postgresql.Driver"; //driver from postgresql
        final String URL = "jdbc:postgresql://localhost:5432/testdb"; //url to your database

        final String USER = "postgres";
        final String PASSWORD = "1111";
        Input input = new Input();
        DatabaseManipulation dbm = new DatabaseManipulation(USER, PASSWORD, URL, DRIVER);
        boolean work = true;
        while (work){
            System.out.println("Выбирите действие:\n1.Создать таблицу\n2.Удалить таблицу\n3.Посмотрь все таблицы\n0.Завершить программу");
            switch (input.intIn()){
                case 0-> work = false;
                case 1->{
                    System.out.println("Введите название таблицы(Без цифр): ");
                    dbm.createNewTable(input.strIn());
                }
                case 2->{
                    dbm.allTables();
                    System.out.println("0.Отменить удаление\nВведите номер удаляемой таблицы");
                    List<String> list = dbm.getAllTables();
                    int num;
                    do{
                        num = input.intIn() - 1;
                    }while (num >= list.size() || num < -1);
                    if (num == -1) break;
                    dbm.deleteTable(list.get(num));
                }
                case 3->{
                    dbm.allTables();
                    System.out.println();
                }
            }
        }
    }
}
