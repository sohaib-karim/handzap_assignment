package handzap.com.handzap.src.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import handzap.com.handzap.R;
import handzap.com.handzap.src.ui.main.views.MainView;

public class CustomDialog {

    private WeakReference<Activity> weakReferenceContext;
    private MainView mainView;
    private Dialog dialog;
    private String selectedRadioButton = null;

    public CustomDialog(Activity activity, MainView mainView){
        this.weakReferenceContext = new WeakReference<>(activity);
        this.mainView = mainView;
    }

    public void showDialog(final String dialogType, String firstOption, String secondOption, String thirdOption, String title){
        Activity activity = weakReferenceContext.get();
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.setTitle(title);

        TextView tv_title = dialog.findViewById(R.id.title);
        final RadioGroup radio_group = dialog.findViewById(R.id.radio_group);
        RadioButton rb1 = dialog.findViewById(R.id.rb1);
        RadioButton rb2 = dialog.findViewById(R.id.rb2);
        RadioButton rb3 = dialog.findViewById(R.id.rb3);
        TextView select = dialog.findViewById(R.id.btn_select);
        TextView deny = dialog.findViewById(R.id.btn_deny);


        tv_title.setText(title);
        rb1.setText(firstOption);
        rb2.setText(secondOption);
        rb3.setText(thirdOption);

        radio_group.clearCheck();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = radioGroup.findViewById(checkedId);

                if (null != rb) {
                    selectedRadioButton = rb.getText().toString();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mainView) {
                    if (dialogType.equals("PAYMENT"))
                        mainView.setPaymentMethod(selectedRadioButton);
                    else if(dialogType.equals("RATE"))
                        mainView.setSelectedRate(selectedRadioButton);
                    else
                        mainView.setSelectedJobTerm(selectedRadioButton);
                }
                dialog.dismiss();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();



    }




}
