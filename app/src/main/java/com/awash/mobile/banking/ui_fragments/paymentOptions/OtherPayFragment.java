package com.awash.mobile.banking.ui_fragments.paymentOptions;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.awash.mobile.banking.Classes.Constants;
import com.awash.mobile.banking.R;
import com.awash.mobile.banking.ui_fragments.BaseFragment;
import com.suke.widget.SwitchButton;


public class OtherPayFragment extends BaseFragment implements
        SwitchButton.OnCheckedChangeListener,
        View.OnClickListener{

    private View root;
    private SwitchButton reasonSw;
    private EditText addReasonF,accountNoF,amountF,pinF;
    private Button btnOtherPay;

    public OtherPayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_other_pay, container, false);

        reasonSw    = root.findViewById(R.id.switchAddReason);
        addReasonF  = root.findViewById(R.id.reasonF);
        accountNoF  = root.findViewById(R.id.accountNo);
        amountF     = root.findViewById(R.id.amountF);
        btnOtherPay = root.findViewById(R.id.btnOtherPay);
        pinF        = root.findViewById(R.id.pinF);

        reasonSw.setOnCheckedChangeListener(this);
        btnOtherPay.setOnClickListener(this);

        return root;
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
       handleSwitch(isChecked,view,addReasonF);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnOtherPay){

            Intent dialIntent = new Intent(Intent.ACTION_DIAL);

            int account = getCurrentAcc();

            String accountNo = accountNoF.getText().toString();
            String amount = amountF.getText().toString();
            String pin    = pinF.getText().toString();
            String reason = addReasonF.getText().toString();

            if (isPhonePermissionsGranted()){

                boolean isCorrect = checkData(accountNo,amount,pin,reason);

                if (isCorrect){

                    if (reasonSw.isChecked()){

                    }else {

                    }

                    showConfirmation(Constants.TYPE_OTHER_PAYMENT,amount,accountNo,dialIntent).show();
                }

            }else
                askPhonePermission();
        }
    }

    private boolean checkData(String accountNo, String amount,
                              String pin, String reason) {

        if (accountNo.isEmpty()) {
            accountNoF.setError("Account Number can not be empty");
            return false;
        }
        if (amount.isEmpty()) {
            amountF.setError("Amount can not be empty");
            return false;
        }
        if (reasonSw.isChecked()) {

            if (reason.isEmpty()) {
                addReasonF.setError("Please x reason for payment");
                return false;
            }
        }
        if (pin.isEmpty()) {
            pinF.setError("Pin can not be empty");
            return false;
        }

        return true;
    }
}
