package handzap.com.handzap.src.ui.main.presenter;

import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import handzap.com.handzap.src.ui.main.views.MainView;

public class MainPresenter {

    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void setCurrencyOnLoad(String currencyName, String currencyCode) {
        setCurrency(currencyCode, ExtendedCurrency.getCurrencyByName(currencyName).getFlag());
    }

    public void setCurrency(String currencyCode, int flagIconCode) {
        if (null != mainView) {
            mainView.setCurrency(currencyCode, flagIconCode);
        }
    }

    public void getSelectedDate(int year, int monthOfYear, int dayOfMonth){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E dd MMM");
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);

        if (null != mainView)
            mainView.setSelectedDate(dateFormatter.format(newDate.getTime()));
    }

    public void onDestroy(){
        mainView = null;
    }
}
