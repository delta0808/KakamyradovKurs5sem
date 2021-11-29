package main;

import dialogs_start.MainJobPreparer;

// TODO Класс клиента, который использует функционал фасада MainJobPreparer через статический интерфейс
public class Main {
    public static void main(String[] args) {
        //Работа строиться на шаблоне проектирования "Фасад".
        // Вся функциональность проекта сводится к вызову 1-го статического метода
        MainJobPreparer.startCourseWork();
    }
}
