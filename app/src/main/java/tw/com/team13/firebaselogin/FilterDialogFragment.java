package tw.com.team13.firebaselogin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.com.team13.model.Restaurant;
import com.google.firebase.firestore.Query;

/**
 * @author Chun-Kai Kao on 2018/5/26 01:34
 * @github http://github.com/cckaron
 */

/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";

    public interface FilterListener {

        void onFilter(Filters filters);

    }

    private View mRootView;

    @BindView(R.id.spinner_category)
    Spinner mCategorySpinner;

    @BindView(R.id.spinner_city1)
    Spinner mCitySpinner1;

    @BindView(R.id.spinner_city2)
    Spinner mCitySpinner2;

    @BindView(R.id.spinner_city3)
    Spinner mCitySpinner3;

    @BindView(R.id.spinner_city4)
    Spinner mCitySpinner4;

    @BindView(R.id.spinner_city5)
    Spinner mCitySpinner5;

    @BindView(R.id.spinner_sort)
    Spinner mSortSpinner;

    @BindView(R.id.spinner_price)
    Spinner mPriceSpinner;

    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filters, container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.button_search)
    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
            Log.d(TAG, "onSearchClicked:");
        }

        dismiss();
    }

    @OnClick(R.id.button_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedCategory() {
        String selected = (String) mCategorySpinner.getSelectedItem();
        if (getString(R.string.value_any_category).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedCity() {
        String selected1 = (String) mCitySpinner1.getSelectedItem();
        if (getString(R.string.value_any_greenLine).equals(selected1)) {
//            return null;
        } else {
            return selected1;
        }

        String selected2 = (String) mCitySpinner2.getSelectedItem();
        if (getString(R.string.value_any_redLine).equals(selected2)) {
//            return null;
        } else {
            return selected2;
        }

        String selected3 = (String) mCitySpinner3.getSelectedItem();
        if (getString(R.string.value_any_orangeLine).equals(selected3)) {
//            return null;
        } else {
            return selected3;
        }
        String selected4 = (String) mCitySpinner4.getSelectedItem();
        if (getString(R.string.value_any_blueLine).equals(selected4)) {
//            return null;
        } else {
            return selected4;
        }
        String selected5 = (String) mCitySpinner5.getSelectedItem();
        if (getString(R.string.value_any_brownLine).equals(selected5)) {
//            return null;
        } else {
            return selected5;
        }
        return null;
    }

    private int getSelectedPrice() {
        String selected = (String) mPriceSpinner.getSelectedItem();
        if (selected.equals(getString(R.string.price_1))) {
            return 90;
        } else if (selected.equals(getString(R.string.price_2))) {
            return 110;
        } else if (selected.equals(getString(R.string.price_3))) {
            return 410;
        } else {
            return -1;
        }
    }

    @Nullable
    private String getSelectedSortBy() {
        String selected = (String) mSortSpinner.getSelectedItem();
        String priceSelected = (String) mPriceSpinner.getSelectedItem();
        if (!priceSelected.equals(getString(R.string.value_any_price))){
            return Restaurant.FIELD_PRICE;
        }
        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Restaurant.FIELD_AVG_RATING;
        } if (getString(R.string.sort_by_price).equals(selected)) {
            return Restaurant.FIELD_PRICE;
        } if (getString(R.string.sort_by_popularity).equals(selected)) {
            return Restaurant.FIELD_POPULARITY;
        }

        return null;
    }

    @Nullable
    private Query.Direction getSortDirection() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Query.Direction.DESCENDING;
        } if (getString(R.string.sort_by_price).equals(selected)) {
            return Query.Direction.ASCENDING;
        } if (getString(R.string.sort_by_popularity).equals(selected)) {
            return Query.Direction.DESCENDING;
        }

        return null;
    }

    public void resetFilters() {
        if (mRootView != null) {
            mCategorySpinner.setSelection(0);
            mCitySpinner1.setSelection(0);
            mCitySpinner2.setSelection(0);
            mCitySpinner3.setSelection(0);
            mCitySpinner4.setSelection(0);
            mCitySpinner5.setSelection(0);
            mPriceSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();
        Log.d(TAG, "getFilters:");

        if (mRootView != null) {
            filters.setCategory(getSelectedCategory());
            filters.setCity(getSelectedCity());
            filters.setPrice(getSelectedPrice());
            Log.d(TAG, "getFilters: the price I get is" + getSelectedPrice());
            filters.setSortBy(getSelectedSortBy());
            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }
}
