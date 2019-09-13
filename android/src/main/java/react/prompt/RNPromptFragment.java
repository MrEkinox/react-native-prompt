package react.prompt;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import 	android.widget.Button;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Field;
import java.lang.Object;
import javax.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import java.lang.Exception;

public class RNPromptFragment extends DialogFragment implements DialogInterface.OnClickListener {

    /* package */ static final String ARG_TITLE = "title";
    /* package */ static final String ARG_MESSAGE = "message";
    /* package */ static final String ARG_BUTTON_POSITIVE = "button_positive";
    /* package */ static final String ARG_BUTTON_NEGATIVE = "button_negative";
    /* package */ static final String ARG_BUTTON_NEUTRAL = "button_neutral";
    /* package */ static final String ARG_TYPE = "type";
    /* package */ static final String ARG_COLOR = "color";
    /* package */ static final String ARG_DEFAULT_VALUE = "defaultValue";
    /* package */ static final String ARG_PLACEHOLDER = "placeholder";

    private EditText mInputText;

    public enum PromptTypes {
        TYPE_DEFAULT("default"),
        PLAIN_TEXT("plain-text"),
        SECURE_TEXT("secure-text"),
        NUMERIC("numeric"),
        EMAIL_ADDRESS("email-address"),
        PHONE_PAD("phone-pad");

        private final String mName;

        PromptTypes(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    private
    @Nullable
    RNPromptModule.PromptFragmentListener mListener;

    public RNPromptFragment() {
        mListener = null;
    }

    public void setListener(@Nullable RNPromptModule.PromptFragmentListener listener) {
        mListener = listener;
    }

    public Dialog createDialog(Context activityContext, Bundle arguments) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(activityContext);

        builder.setTitle(arguments.getString(ARG_TITLE));

        if (arguments.containsKey(ARG_BUTTON_POSITIVE)) {
            builder.setPositiveButton(arguments.getString(ARG_BUTTON_POSITIVE), this);
        }
        if (arguments.containsKey(ARG_BUTTON_NEGATIVE)) {
            builder.setNegativeButton(arguments.getString(ARG_BUTTON_NEGATIVE), this);
        }
        if (arguments.containsKey(ARG_BUTTON_NEUTRAL)) {
            builder.setNeutralButton(arguments.getString(ARG_BUTTON_NEUTRAL), this);
        }
        if (arguments.containsKey(ARG_MESSAGE)) {
            builder.setMessage(arguments.getString(ARG_MESSAGE));
        }

        AlertDialog alertDialog = builder.create();

        if (arguments.containsKey(ARG_TYPE) && !arguments.getString(ARG_TYPE).equals("default")) {
            // input style
            final EditText input;
            input = new EditText(activityContext);

            // input type
            int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
            if (arguments.containsKey(ARG_TYPE)) {
                String typeString = arguments.getString(ARG_TYPE);
                if (typeString != null) {
                    switch (typeString) {
                        case "secure-text":
                            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                            break;
                        case "numeric":
                            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER;
                            break;
                        case "email-address":
                            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                            break;
                        case "phone-pad":
                            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE;
                            break;
                        case "plain-text":
                        default:
                            type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
                    }
                }
            }
            input.setInputType(type);
            if (arguments.containsKey(ARG_DEFAULT_VALUE)) {
                String defaultValue = arguments.getString(ARG_DEFAULT_VALUE);
                if (defaultValue != null) {
                    input.setText(defaultValue);
                    int textLength = input.getText().length();
                    input.setSelection(textLength, textLength);
                }
            }
            if (arguments.containsKey(ARG_PLACEHOLDER)) {
                input.setHint(arguments.getString(ARG_PLACEHOLDER));
            }


            if (arguments.containsKey(ARG_COLOR)) {
                int color = Color.parseColor(arguments.getString(ARG_COLOR));
                Drawable drawable = input.getBackground();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                input.setBackground(drawable);
            }

            alertDialog.setView(input, 50, 0, 50, 0);
            mInputText = input;
        }
        return alertDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = this.createDialog(getActivity(), getArguments());
        if (mInputText != null && mInputText.requestFocus()) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mListener != null) {
            if (mInputText != null) {
                mListener.onConfirm(which, mInputText.getText().toString());
            } else {
                mListener.onConfirm(which, "");
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) {
            mListener.onDismiss(dialog);
        }
    }
}
