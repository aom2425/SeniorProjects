package com.example.seniorproject.helper;


import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class InputValidation {
    private static Context context;
    public InputValidation(Context context) {
        this.context = context;
    }

    public static boolean isInputEditTextFilled(EditText textInputEditText) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            //textInputLayout.setError(message);
            textInputEditText.setError("This field can not be blank");
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            //textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean isInputEditTextEmail(EditText textInputEditText) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            //textInputLayout.setError(message);
            textInputEditText.setError("This field can not be blank");
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            //textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText textInputEditText1, EditText textInputEditText2) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            //textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            //textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    private static void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}