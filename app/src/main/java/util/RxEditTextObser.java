package util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by KIM on 2018-05-29.
 */

public class RxEditTextObser {
    public static PublishSubject<String> create(View view) {
        final PublishSubject<String> source = PublishSubject.create();
        EditText editText = (EditText)view;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                source.onNext(s.toString());
            }
        });
        return source;
    }
}
