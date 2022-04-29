package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    Scanner scanner;
    Pattern pattern = Pattern.compile("[0-9]");

    public String strIn(){
        scanner = new Scanner(System.in);
        String str;
        Matcher matcher;
        boolean hasNum;
        do{
            str = scanner.nextLine();
            matcher = pattern.matcher(str);
            hasNum = matcher.find();
            if(hasNum) System.out.println("Обнаружена цифра, повторите ввод");
        }while(hasNum);
        return str;
    }

    public int intIn(){
        while (true){
            try {
                scanner = new Scanner(System.in);
                int num = scanner.nextInt();
                if(num > -1) return num;
                else System.out.println("Число должно быть положительным");
            }
            catch (Exception exception){
                System.out.println("Введите целое число");
            }
        }
    }
}
