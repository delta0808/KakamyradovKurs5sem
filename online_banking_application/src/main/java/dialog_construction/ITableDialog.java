package dialog_construction;

import model.Model;

import java.util.ArrayList;

// TODO Паттерн адаптер: Интерфейс Target, содержит метод update, который адаптирует updateData метод
public interface ITableDialog {
    ArrayList<Model> update();
}
