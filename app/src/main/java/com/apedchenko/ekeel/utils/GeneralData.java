package com.apedchenko.ekeel.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GeneralData {
    public static final String requestUrlGlobalDictionary = "https://a-capoeira.com/ekeel/api/global_dictionary.php";
    private static final String PARAM_FIND_WORD                         = "findWord";
    private static final String PARAM_ADD_GLOBAL_FIRST_FORM_WORD        = "addGlobalNimetav";
    private static final String PARAM_ADD_GLOBAL_SECOND_FORM_WORD       = "addGlobalOmastav";
    private static final String PARAM_ADD_GLOBAL_THIRD_FORM_WORD        = "addGlobalOsastav";
    private static final String PARAM_ADD_GLOBAL_TRANSLATE_FOR_WORD     = "addGlobalTranslate";
    private static final String PARAM_ADD_GLOBAL_TYPE_WORD              = "addGlobalWordType";
    private static final String PARAM_GET_GLOBAL_RANDOM_WORD            = "getGlobalRandom";
    private static final String PARAM_GET_GLOBAL_RANDOM_WORD_WITH_TYPE  = "getWordType";

    //     hide the virtual keyboard
    public static void hideSoftKeyBoard(Activity activity) {
        // region specifying the object
        InputMethodManager service = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        // verify if the soft keyboard is open
        if (Objects.requireNonNull(service).isAcceptingText()) service.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus().getWindowToken()), 0);
    }

    // read JSON
//    public static String

}
