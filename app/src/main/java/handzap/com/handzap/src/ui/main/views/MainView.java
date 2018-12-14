package handzap.com.handzap.src.ui.main.views;

public interface MainView {

    void showProgressBar();

    void hideProgressBar();

    void setPaymentMethod(String paymentMethod);

    void setSelectedRate(String rate);

    void setSelectedJobTerm(String jobTerm);

    void showBottomMenu();

    void hideBottomMenu();

    void showCurrencyFragment();

    void setCurrency(String currencyCode, int flagIconCode);

    void setSelectedDate(String date);

    

}
