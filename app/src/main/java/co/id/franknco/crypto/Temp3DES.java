package co.id.franknco.crypto;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import co.id.franknco.crypto.TripleDES;

/**
 * Created by GSS-NB-2016-0012 on 9/27/2017.
 */

public class Temp3DES {
    private TripleDES tripleDES;
    Context _context;
    public Temp3DES(Activity mContext) {
        tripleDES = new TripleDES();
    }

    public Temp3DES(Context context) {
        this._context = context;
        tripleDES = new TripleDES();
    }

    public String encrypt(String word) {
        // UNTUK KEY , SELALU SAMA,AMBIL DARI DB NANTINYA
        String hs3DESKey = "313233343536373839313233343536373839313233343536";
        byte[] ba3DESKey = tripleDES.hexStringToByteArray(hs3DESKey);
        String hsPlainMsg = null;
        String hsEncMsg;
        byte[] baEncMsg;
        try {
            hsPlainMsg = tripleDES.stringToHex2(word);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] baPlainMsg = tripleDES.hexStringToByteArray(hsPlainMsg);


        //ENCRY
        baEncMsg = tripleDES.encrypt3DES(baPlainMsg, ba3DESKey);
         hsEncMsg = tripleDES.bytesToHex(baEncMsg);

        return hsEncMsg;
    }

    public String decrypt(String word) {
        // UNTUK KEY , SELALU SAMA,AMBIL DARI DB NANTINYA
        String hs3DESKey = "313233343536373839313233343536373839313233343536";
        byte[] ba3DESKey = tripleDES.hexStringToByteArray(hs3DESKey);
        String hsDecMsg;
        byte[] baDecMsg;

        //DECRY
        byte[] tempWord = tripleDES.hexStringToByteArray(word);
        baDecMsg = tripleDES.decrypt3DES(tempWord, ba3DESKey);
         hsDecMsg = tripleDES.bytesToHex(baDecMsg);
        String AAAAA = tripleDES.convertHexToString(hsDecMsg);
        String convertedString = AAAAA.replaceAll("\u0000", "");

        return convertedString;
    }

    public String decrypt_cardkey(String word) {
        // UNTUK KEY , SELALU SAMA,AMBIL DARI DB NANTINYA
        String hs3DESKey = "313233343536373839313233343536373839313233343536";
        byte[] ba3DESKey = tripleDES.hexStringToByteArray(hs3DESKey);
        String hsDecMsg ;
        byte[] baDecMsg;

        //DECRY
        byte[] tempWord = tripleDES.hexStringToByteArray(word);
        baDecMsg = tripleDES.decrypt3DES(tempWord, ba3DESKey);
        hsDecMsg = tripleDES.bytesToHex(baDecMsg);
        String AAAAA = tripleDES.convertHexToString(hsDecMsg);
        String convertedString = AAAAA.replaceAll("\u0000", "");
          return convertedString;
    }

    public String decrypt_cr(String word, String cardkey) {
        String hsDecMsg;
        byte[] baDecMsg;


        //DECRY
        byte[] tempWord = tripleDES.hexStringToByteArray(word);
        byte[] cardkey2 = tripleDES.hexStringToByteArray(cardkey);


        baDecMsg = tripleDES.decrypt3DES(tempWord, cardkey2);


        hsDecMsg = tripleDES.bytesToHex(baDecMsg);
        String AAAAA = tripleDES.convertHexToString(hsDecMsg);
        String convertedString = AAAAA.replaceAll("\u0000", "");

        return convertedString;

    }

    public String encrypt_cr(String word) {
        // UNTUK KEY , SELALU SAMA,AMBIL DARI DB NANTINYA
        String hs3DESKey = "592377C8813B490D657E89B04C0748D1";
        byte[] ba3DESKey = tripleDES.hexStringToByteArray(hs3DESKey);
        String hsPlainMsg = null;
        String hsEncMsg;
        byte[] baEncMsg;
        try {
            hsPlainMsg = tripleDES.stringToHex2(word);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] baPlainMsg = tripleDES.hexStringToByteArray(hsPlainMsg);


        //ENCRY
        baEncMsg = tripleDES.encrypt3DES(baPlainMsg, ba3DESKey);
        hsEncMsg = tripleDES.bytesToHex(baEncMsg);

        return hsEncMsg;
    }


}
