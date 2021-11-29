package data_base;

import model.*;
import validation.Validator;

public class FieldsTransformation {


    public String[] newData(Model model){
        switch (model.getClass().getName()){
            case "model.TransactionModel":                return newDataTransaction(model);
            case "model.UserModel":                   return newDataUser(model);
            default: case "model.FamilyAccountModel":     return newDataFamilyAccount(model);
        }
    }

    private String[] newDataTransaction(Model modelI){
        TransactionModel model = (TransactionModel)modelI;
        String answer[] = new String[]{
                model.getComment(),
                String.valueOf(model.getSum()),
                model.getDate(),
                String.valueOf(model.getIdUser()),
                String.valueOf(model.getIdType())
        };
        return answer;
    }

    private String[] newDataUser(Model modelI){
        UserModel model = (UserModel)modelI;
        String answer[] = new String[]{
                model.getFirstName(),
                model.getLastName(),
                model.getLogin(),
                model.getPassword(),
                model.getPhoneNumber(),
                model.getIsBlocked(),
                String.valueOf(model.getIdFamilyAccount()),
                String.valueOf(model.getIdAuthority())

        };
        return answer;
    }

    private String[] newDataFamilyAccount(Model modelI){
        FamilyAccountModel model = (FamilyAccountModel)modelI;
        String answer[] = new String[]{
                String.valueOf(model.getSum()),
                model.getFamilyLogin(),
                model.getFamilyPassword(),
                String.valueOf(model.getIdCurrency())

        };
        return answer;
    }
}
