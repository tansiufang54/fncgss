package co.id.franknco.interfaces;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import co.id.franknco.model.Faq;

/**
 * Created by darwin on 1/26/18.
 */

public interface FaqListener {
    void onResult(List<Faq> list);
}
