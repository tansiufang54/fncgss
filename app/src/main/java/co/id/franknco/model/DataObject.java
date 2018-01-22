package co.id.franknco.model;

/**
 * Created by GSS-NB-2017-0013 on 10/2/2017.
 */

public class DataObject {
    private int imageId;
    private String card_number;
    private String card_user;
    private String card_saldo;
    private String card_expired;
    private String card_id;

    public DataObject(int imageId, String card_number, String card_user,
                      String card_saldo, String card_expired, String card_id) {
        this.imageId = imageId;
        this.card_number = card_number;
        this.card_user = card_user;
        this.card_saldo = card_saldo;
        this.card_expired = card_expired;
        this.card_id = card_id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_user() {
        return card_user;
    }

    public void setCard_user(String card_user) {
        this.card_user = card_user;
    }

    public String getCard_saldo() {
        return card_saldo;
    }

    public void setCard_saldo(String card_saldo) {
        this.card_saldo = card_saldo;
    }

    public String getCard_expired() {
        return card_expired;
    }

    public void setCard_expired(String card_expired) {
        this.card_expired = card_expired;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
}
