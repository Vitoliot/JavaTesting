package ru.vitoliot.testing;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculation {
    public static void main(String[] args)
    {
        Calculation calculation = new Calculation();

        LocalTime[] startTimes = new LocalTime[] {
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 50)
        };

        int[] durations = { 60, 30, 10, 10, 40 };

        String[] array = calculation.getPeriods(startTimes, durations, 30, LocalTime.of(8, 0), LocalTime.of(18, 0));
        System.out.println(Arrays.toString(array));

    }


    public String[] getPeriods(LocalTime[] startTimes, int[] durations, int consultationTime, LocalTime beginWorkingTime, LocalTime endWorkingTime){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

        //список строк
        //в него мы поместим все минуты от начала до конца рабочего дня
        List<String> minutesList = new ArrayList<>();

        //цикл от первого до последнего часа
        for(int i=beginWorkingTime.getHour(); i<=endWorkingTime.getHour(); i++) {
            //цикл от 0 ло 59 минут
            for(int j=0; j<60; j++) {
                //получаем объект LocalTime из часа и минуты в цикле
                LocalTime time = LocalTime.of(i, j);
                //если время >= начала рабочего дня и <= конца рабочего дня
                if(time.equals(beginWorkingTime) || time.isAfter(beginWorkingTime) && time.equals(endWorkingTime) || time.isBefore(endWorkingTime)) {
                    //форматируем и добавляем в список
                    minutesList.add(time.format(format));
                }
            }
        }

        //перебираем все занятые минуты и выставляем на их место null
        for(int i=0; i<startTimes.length; i++) {
            LocalTime start = startTimes[i];
            for(int j=1; j<durations[i]; j++) {
                minutesList.set(minutesList.indexOf(start.plusMinutes(j).format(format)), null);
            }
        }

        //список с финальными отрезками
        List<String> finalList = new ArrayList<>();

        String startMinute = null;
        int counter = 0;

        //ищем отрезки без null длиной consultationTimeLocalTime
        for(String minute : minutesList) {
            if(minute == null) {
                counter = 0;
                continue;
            }

            if(counter == 0) {
                startMinute = minute;
            } else if(counter == consultationTime) {
                finalList.add(startMinute + "-" + minute);
                startMinute = minute;
                counter = 1;
                continue;
            }

            counter++;
        }

        //переводим список в массив
        return finalList.toArray(new String[0]);
    }
}
