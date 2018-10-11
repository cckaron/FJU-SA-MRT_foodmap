package tw.com.team13.Home;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.com.team13.Adapter.RestaurantAdapter;
import tw.com.team13.Login.LoginActivity;
import tw.com.team13.Utils.BottomNavigationViewHelper;
import tw.com.team13.firebaselogin.FilterDialogFragment;
import tw.com.team13.firebaselogin.Filters;
import tw.com.team13.firebaselogin.HomeActivity;
import tw.com.team13.firebaselogin.R;
import tw.com.team13.firebaselogin.RestaurantDetailActivity;
import tw.com.team13.model.Rating;
import tw.com.team13.model.Restaurant;
import tw.com.team13.util.RatingUtil;
import tw.com.team13.util.RestaurantUtil;
import tw.com.team13.viewmodel.MainActivityViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * @author Chun-Kai Kao on 2018/5/26 01:34
 * @github http://github.com/cckaron
 */

public class MessagesFragment extends Fragment{

    private static final String TAG = "MessageFragment";

    private ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_messenger, container, false);
        

        ButterKnife.bind(this,view);


        return view;
    }

}
