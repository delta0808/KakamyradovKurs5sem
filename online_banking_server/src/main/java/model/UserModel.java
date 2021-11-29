package model;

public class UserModel implements Model {
    public final static String[] SIMPLE_FIELD_TRANSLATION = {
            "Имя", "Фамилия", "Логин", "Пароль", "Номер",
            "Заблокирован?", "ИДКомпани", "ИДРоли"
    };
    public final static String[] SIMPLE_FIELD = {
            "firstName", "lastName", "login", "password", "phoneNumber",
            "isBlocked", "idFamilyAccount", "idAuthority"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE = {true, true, true, true, true, true, false, false};

    public final static String[] SIMPLE_FIELD_TRANSLATION_SELECT = {
            "Имя", "Фамилия", "Логин", "Пароль", "Номер", "Заблокирован?", "ИДКомпани",
            "ИДРоли", "Бюджет", "ЛогинКомпании", "ПарольКомпании", "ИДВалюты", "КодВалюты",
            "НазваниеВалюты", "Роль"
    };
    public final static String[] SIMPLE_FIELD_SELECT = {
            "firstName", "lastName", "login", "password", "phoneNumber", "isBlocked", "idFamilyAccount",
            "idAuthority", "sum", "familyLogin", "familyPassword", "idCurrency", "currencyCode",
            "currencyName", "authority"
    };
    public final static boolean[] SIMPLE_FIELD_TYPE_SELECT = {true, true, true, true, true, true, false, false,
            false, true, true, false, true, true, true};

    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String phoneNumber;
    private String isBlocked;
    private int idFamilyAccount;
    private int idAuthority;

    private double sum;
    private String familyLogin;
    private String familyPassword;
    private int idCurrency;
    private String currencyCode;
    private String currencyName;
    private String authority;

    public UserModel() {
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
        return "user";
    }

    public static String getViewNameStatic() {
        return getTableNameStatic() + "_view";
    }

    public UserModel(int id, String firstName, String lastName, String login, String password,
                         String phoneNumber, String isBlocked, int idFamilyAccount, int idAuthority) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.idFamilyAccount = idFamilyAccount;
        this.idAuthority = idAuthority;
    }

    public UserModel(int id, String firstName, String lastName, String login, String password,
                         String phoneNumber, String isBlocked, int idFamilyAccount, int idAuthority,
                         double sum, String familyLogin, String familyPassword, int idCurrency, String currencyCode,
                         String currencyName, String authority) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.idFamilyAccount = idFamilyAccount;
        this.idAuthority = idAuthority;
        this.sum = sum;
        this.familyLogin = familyLogin;
        this.familyPassword = familyPassword;
        this.idCurrency = idCurrency;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.authority = authority;
    }

    public String[] getSIMPLE_FIELDByRus() {
        return SIMPLE_FIELD_TRANSLATION;
    }

    public String[] getSIMPLE_FIELD() {
        return SIMPLE_FIELD;
    }

    public boolean[] getIsStr() {
        return SIMPLE_FIELD_TYPE;
    }

    public String[] getSIMPLE_FIELDByRusSelect() {
        return SIMPLE_FIELD_TRANSLATION_SELECT;
    }

    public String[] getSIMPLE_FIELDSelect() {
        return SIMPLE_FIELD_SELECT;
    }

    public boolean[] getIsStrSelect() {
        return SIMPLE_FIELD_TYPE_SELECT;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public int getIdFamilyAccount() {
        return idFamilyAccount;
    }

    public int getIdAuthority() {
        return idAuthority;
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

    public String getAuthority() {
        return authority;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setIdFamilyAccount(int idFamilyAccount) {
        this.idFamilyAccount = idFamilyAccount;
    }

    public void setIdAuthority(int idAuthority) {
        this.idAuthority = idAuthority;
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

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
