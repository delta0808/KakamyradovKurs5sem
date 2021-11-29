package model;

public class TransactionModel implements Model {
    public final static String[] SIMPLE_FIELD_TRANSLATION = {
            "Коммент", "Бюджет", "Дата", "ИДПользователя", "ИДТипа"
    };
    public final static String[] SIMPLE_FIELD = {
            "comment", "sum", "date", "idUser", "idType"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE = {true, false, true, false, false};

    public final static String[] SIMPLE_FIELD_TRANSLATION_SELECT = {
            "Коммент", "Бюджет", "Дата", "ИДПользователя", "ИДТипа",
            "Имя", "Фамилия", "ИДКомпании", "КатегорияТип", "НазваниеТипа", "Роль"
    };
    public final static String[] SIMPLE_FIELD_SELECT = {
            "comment", "sum", "date", "idUser", "idType",
            "firstName", "lastName", "idFamilyAccount", "category", "name", "authority"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE_SELECT = {true, false, true, false, false,
            true, true, false, true, true, true};

    private int id;
    private String comment;
    private double sum;
    private String date;
    private int idUser;
    private int idType;

    private String firstName;
    private String lastName;
    private int idFamilyAccount;
    private String category;
    private String name;
    private String authority;

    public TransactionModel() {
    }

    public TransactionModel(int id, String comment, double sum, String date, int idUser, int idType) {
        this.id = id;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.idUser = idUser;
        this.idType = idType;
    }

    public TransactionModel(int id, String comment, double sum, String date, int idUser, int idType,
                            String firstName, String lastName, int idFamilyAccount, String category, String name, String authority) {
        this.id = id;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.idUser = idUser;
        this.idType = idType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idFamilyAccount = idFamilyAccount;
        this.category = category;
        this.name = name;
        this.authority = authority;
    }

    @Override
    public String getTableName() {
        return getTableNameStatic();
    }

    @Override
    public String getViewName() {
        return getViewNameStatic();
    }

    public static String getTableNameStatic() {
        return "transaction";
    }

    public static String getViewNameStatic() {
        return getTableNameStatic() + "_view";
    }

    @Override
    public String[] getSIMPLE_FIELDByRus() {
        return SIMPLE_FIELD_TRANSLATION;
    }

    @Override
    public String[] getSIMPLE_FIELD() {
        return SIMPLE_FIELD;
    }

    @Override
    public boolean[] getIsStr() {
        return SIMPLE_FIELD_TYPE;
    }

    public String[] getSIMPLE_FIELDByRusSelect() {
        return SIMPLE_FIELD_TRANSLATION_SELECT;
    }

    @Override
    public String[] getSIMPLE_FIELDSelect() {
        return SIMPLE_FIELD_SELECT;
    }

    @Override
    public boolean[] getIsStrSelect() {
        return SIMPLE_FIELD_TYPE_SELECT;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public double getSum() {
        return sum;
    }

    public String getDate() {
        return date;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdType() {
        return idType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getIdFamilyAccount() {
        return idFamilyAccount;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdFamilyAccount(int idFamilyAccount) {
        this.idFamilyAccount = idFamilyAccount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
