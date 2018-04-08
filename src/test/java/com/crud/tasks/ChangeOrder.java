package com.crud.tasks;

public class ChangeOrder {

    public int change(int number) {

        String number1 = String.valueOf(number);
        String number2= "";

        for (int i = number1.length()-1; i >=0; i--) {
                number2 = number2 + number1.charAt(i);
        }
        int number3 = Integer.parseInt(number2);
    return number3;
    }

    public static void main(String[] args) {
        ChangeOrder orderChange = new ChangeOrder();
        int result = orderChange.change(179353518);

        System.out.println(result);

    }
}

