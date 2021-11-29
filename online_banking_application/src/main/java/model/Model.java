package model;

public interface Model {
    void setId(int id);
    int getId();
    String getTableName();
    String getViewName();

    String[] getSIMPLE_FIELDByRus();
    String[] getSIMPLE_FIELD();
    boolean[] getIsStr();
    String[] getSIMPLE_FIELDByRusSelect();
    String[] getSIMPLE_FIELDSelect();
    boolean[] getIsStrSelect();
}
