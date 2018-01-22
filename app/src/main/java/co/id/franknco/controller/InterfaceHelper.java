package co.id.franknco.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by GSP-NB-2015-0005 on 17-Dec-17.
 */

public class InterfaceHelper {


    public static volatile InterfaceHelper svInstanceIH;

    public InterfaceHelper() {
    }

    public static InterfaceHelper getInstance()
    {
        if(svInstanceIH == null)
        {
            svInstanceIH = new InterfaceHelper();
        }
        return svInstanceIH;
    }

    public Boolean cekInput(EditText anEditText)
    {
        String temp;
        temp = anEditText.getText().toString();
        View focusView;
        if(TextUtils.isEmpty(temp))
        {
            anEditText.setError("Value must be filled!");
            focusView = anEditText;
            return false;
        }
        else return true;
    }

    public void makeToastShort(Context aContext, String toastMessage)
    {
        Toast.makeText(aContext, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(Context aContext, String toastMessage)
    {
        Toast.makeText(aContext, toastMessage, Toast.LENGTH_LONG).show();
    }

    public void makeSnackbarWithAction(View aView, String aMessage)
    {
        final Snackbar snackbar = Snackbar.make(aView, aMessage, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE);
        snackbar.setAction("X", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        }).setActionTextColor(Color.RED);
        snackbar.show();
    }

    public String encodeBase64(Bitmap aBitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        aBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String result = Base64.encodeToString(b, Base64.DEFAULT);
        return result;
    }

    public Bitmap decodeBase64(String aStringPic)
    {
        byte[] b = Base64.decode(aStringPic, Base64.DEFAULT);
        Bitmap result = BitmapFactory.decodeByteArray(b,0,b.length);
        return result;
    }

    public void btnPlusQty(EditText anEditText)
    {
        if(anEditText.getText().toString().trim().equals(""))
        {
            anEditText.setText(String.valueOf(0));
        }
        else {
            int inputQty = Integer.parseInt(anEditText.getText().toString());
            inputQty += 1;
            anEditText.setText(String.valueOf(inputQty));
        }
    }

    public void btnMinusQty(EditText anEditText)
    {
        if(anEditText.getText().toString().trim().equals(""))
        {
            anEditText.setText(0);
        }
        else {
            int inputQty = Integer.parseInt(anEditText.getText().toString());
            if(inputQty == 0){
                anEditText.setText(String.valueOf(0));
            }
            else
            {
                inputQty -= 1;
                anEditText.setText(String.valueOf(inputQty));
            }
        }

    }

    public String numberFormat(int aNumber)
    {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(aNumber);
    }
    public String eraseNumberFormat(String aNumber)
    {
        String amountTemp;
        if (aNumber.contains(",")) {
            amountTemp = aNumber.replaceAll(",", "");
        } else {
            amountTemp = aNumber;
        }
        return amountTemp;
    }
    public void LogE(String Tag, String message)
    {

    }

    //this one for expanding the listview or show all List View's child content without scrolling
    public static void setListViewHeight(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter==null)
        {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = null;

        for(int i = 0; i<listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if(i == 0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1 ));
        listView.setLayoutParams(params);
    }

    public String monthConvert(String mm)
    {
        String mmm = "";
        switch (mm)
        {
            case "01":
                mmm = "Jan";
                break;

            case "02":
                mmm = "Feb";
                break;

            case "03":
                mmm = "Mar";
                break;

            case "04":
                mmm = "Apr";
                break;

            case "05":
                mmm = "May";
                break;

            case "06":
                mmm = "Jun";
                break;

            case "07":
                mmm = "Jul";
                break;

            case "08":
                mmm = "Aug";
                break;

            case "09":
                mmm = "Sep";
                break;

            case "10":
                mmm = "Oct";
                break;

            case "11":
                mmm = "Nov";
                break;

            case "12":
                mmm = "Dec";
                break;

            default:
                break;
        }
        return mmm;
    }

}
