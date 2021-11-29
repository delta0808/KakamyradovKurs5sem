package model;

public class FamilyAccountModel implements Model {
    public final static String[] SIMPLE_FIELD_TRANSLATION = {
            "Бюджет", "ЛогинКомпании", "ПарольКомпании", "ИДВалюты"
    };
    public final static String[] SIMPLE_FIELD = {
            "sum", "familyLogin", "familyPassword", "idCurrency"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE = {false, true, true, false};

    public final static String[] SIMPLE_FIELD_TRANSLATION_SELECT = {
            "Бюджет", "ЛогинКомпании", "ПарольКомпании", "ИДВалюты", "КодВалюты", "НазваниеВалюты"
    };
    public final static String[] SIMPLE_FIELD_SELECT = {
            "sum", "familyLogin", "familyPassword", "idCurrency", "currencyCode", "currencyName"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE_SELECT = {false, true, true, false, true, true};

    private int id;
    private double sum;
    private String familyLogin;
    private String familyPassword;
    private int idCurrency;
    private String currencyCode;
    private String currencyName;

    public FamilyAccountModel() {
    }

    public FamilyAccountModel(int id, double sum, String familyLogin, String familyPassword, int idCurrency) {
        this.id = id;
        this.sum = sum;
        this.familyLogin = familyLogin;
        this.familyPassword = familyPassword;
        this.idCurrency = idCurrency;
    }

    public FamilyAccountModel(int id, double sum, String familyLogin, String familyPassword, int idCurrency,
                              String currencyCode, String currencyName) {
        this.id = id;
        this.sum = sum;
        this.familyLogin = familyLogin;
        this.familyPassword = familyPassword;
        this.idCurrency = idCurrency;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
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
        return "familyaccount";
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

    @Override
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

    public double getSum() {
        return sum;
    }

    public String getFamilyLogin() {
        return familyLogin;
    }

    public String getFamilyPassword() {
        return familyPassword;
    }

    public int getIdCurrency() {
        return idCurrency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setFamilyLogin(String familyLogin) {
        this.familyLogin = familyLogin;
    }

    public void setFamilyPassword(String familyPassword) {
        this.familyPassword = familyPassword;
    }

    public void setIdCurrency(int idCurrency) {
        this.idCurrency = idCurrency;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
