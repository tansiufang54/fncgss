package co.id.franknco.model;

/**
 * Created by GSS-NB-2017-0013 on 10/5/2017.
 */

public class CardModel {

    private  String cardnumber="";


    /*********** Set Methods ******************/
    public void setCardNumber(String cardnumber)
    {
        this.cardnumber = cardnumber;
    }


    /*********** Get Methods ****************/
    public String getCardNumber()
    {
        return this.cardnumber;
    }

}


