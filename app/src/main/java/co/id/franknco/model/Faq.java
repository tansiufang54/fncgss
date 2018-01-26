package co.id.franknco.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

/**
 * Created by darwin on 1/26/18.
 */

public class Faq extends BaseModel implements Parcelable, Comparable{

    public String question;
    public String answer;
    public String created_date;
    public String created_by;

    public Faq(){}

    public Faq(JSONObject json) {
        this.question = getString(json, "question");
        this.answer = getString(json, "answer");
        this.created_date = getString(json, "create_date");
        this.created_by = getString(json, "create_by");
    }

    protected Faq(Parcel in) {
        question = in.readString();
        answer = in.readString();
        created_date = in.readString();
        created_by = in.readString();
    }

    public static final Creator<Faq> CREATOR = new Creator<Faq>() {
        @Override
        public Faq createFromParcel(Parcel in) {
            return new Faq(in);
        }

        @Override
        public Faq[] newArray(int size) {
            return new Faq[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(created_date);
        dest.writeString(created_by);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
