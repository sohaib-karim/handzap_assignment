package handzap.com.handzap.src.ui.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import handzap.com.handzap.R;
import handzap.com.handzap.src.ui.main.CustomDialog;
import handzap.com.handzap.src.ui.main.views.MainView;
import handzap.com.handzap.src.ui.main.presenter.MainPresenter;

public class LandingPage extends AppCompatActivity implements MainView, DatePickerDialog.OnDateSetListener{

    @BindView(R.id.my_toolbar)
    Toolbar my_toolbar;

    @BindView(R.id.post_title)
    TextInputLayout text_input_layout_post_title;

    @BindView(R.id.et_post_title)
    TextInputEditText post_title;

    @BindView(R.id.et_post_categories)
    TextInputEditText post_categories;

    @BindView(R.id.et_post_budget)
    TextInputEditText post_budget;

    @BindView(R.id.et_post_currency)
    TextInputEditText post_currency;

    @BindView(R.id.et_post_rate)
    TextInputEditText post_rate;

    @BindView(R.id.et_post_payment)
    TextInputEditText post_payment;

    @BindView(R.id.et_post_location)
    TextInputEditText post_location;

    @BindView(R.id.et_post_s_date)
    TextInputEditText post_start_date;

    @BindView(R.id.et_post_job_term)
    TextInputEditText job_term;

    @BindView(R.id.add_attachment)
    ImageButton add_attachment;

    private MainPresenter presenter;

    private DatePickerDialog datePickerDialog;
    private CustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        ButterKnife.bind(this);

        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_new_post));
        my_toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        presenter = new MainPresenter(this);
        customDialog = new CustomDialog(this, this);

        invalidateOptionsMenu();

        presenter.setCurrencyOnLoad(getString(R.string.indian_currency_name), getString(R.string.indian_currency_code));

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, R.style.TimePickerTheme, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

    }


    @OnClick(R.id.et_post_currency)
    public void changeCurrency() {

        showCurrencyFragment();
    }

    @OnClick(R.id.et_post_s_date)
    public void setPostStartDate(){
        datePickerDialog.show();
    }

    @OnClick(R.id.et_post_payment)
    public void showPaymentMethods() {

        customDialog.showDialog("PAYMENT", getString(R.string.payment_method_no_pref),
                getString(R.string.payment_method_e_payment), getString(R.string.payment_method_cash), getString(R.string.payment_method_title));
    }

    @OnClick(R.id.et_post_rate)
    public void showRateOptions(){
        customDialog.showDialog("RATE", getString(R.string.rate_no_pref),
                getString(R.string.rate_fixed_budget), getString(R.string.rate_hourly_rate), getString(R.string.rate_title));
    }

    @OnClick(R.id.et_post_job_term)
    public void showJobTerms() {
        customDialog.showDialog("JOB_TERM", getString(R.string.job_term_title),
                getString(R.string.job_term_rec_job), getString(R.string.job_term_same_d_job), getString(R.string.job_term_multi_d_job));
    }

    @OnClick(R.id.add_attachment)
    public void onClickAddAttachment(){
        showBottomMenu();
    }

    @OnClick(R.id.et_post_location)
    public void openLocationSelector(){
        //TODO open location auto suggest page
        showSorryToastForIncompleteAssignment();
    }

    @OnClick(R.id.et_post_categories)
    public void openCategoriesSection() {
        showSorryToastForIncompleteAssignment();
        //TODO implement recyclerView with images in grid + flip animation
        //TODO start category section with startActivityForResult()
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setPaymentMethod(String paymentMethod) {
        post_payment.setText(paymentMethod);
    }

    @Override
    public void setSelectedRate(String rate) {
        post_rate.setText(rate);
    }

    @Override
    public void setSelectedJobTerm(String jobTerm) {
        job_term.setText(jobTerm);
    }

    @Override
    public void showBottomMenu() {
        openBottomMenu();
    }

    @Override
    public void hideBottomMenu() {

    }

    @Override
    public void showCurrencyFragment() {
        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                presenter.setCurrency(code, flagDrawableResID);
                picker.dismiss();
            }
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
    }

    @Override
    public void setCurrency(String currencyCode, int flagIconCode) {

        Drawable img = getApplicationContext().getResources().getDrawable(flagIconCode);
        Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
        img = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 50, true));

        post_currency.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
        post_currency.setText(currencyCode);
    }

    @Override
    public void setSelectedDate(String date) {
        post_start_date.setText(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post:
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        styleMenuButton();
        return result;
    }


    private void styleMenuButton() {

        Menu menu = my_toolbar.getMenu();
        MenuItem item = menu.findItem(R.id.post);
        SpannableString s = new SpannableString(item.getTitle());
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.silver)), 0, s.length(), 0);
        item.setTitle(s);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        presenter.getSelectedDate(year,monthOfYear,dayOfMonth);
    }


    private void openBottomMenu() {

        new BottomSheet.Builder(this).sheet(R.menu.bottom_menu_item).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.menu_photo:
                        //TODO open camera to capture photo
                        showSorryToastForIncompleteAssignment();
                        break;
                    case R.id.menu_video:
                        // TODO open camera to capture video
                        showSorryToastForIncompleteAssignment();
                        break;
                    case R.id.menu_library:
                        // TODO open gallery to pick content
                        showSorryToastForIncompleteAssignment();
                        break;
                    case R.id.menu_document:
                        // TODO open documents folder
                        showSorryToastForIncompleteAssignment();
                        break;
                }
            }
        }).show().setCanceledOnTouchOutside(true);
    }

    private void showSorryToastForIncompleteAssignment(){
        Toast.makeText(getApplicationContext(),"did not get time to complete :(",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
