package com.example.bloodbank.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.data.model.reset.Reset;
import com.example.bloodbank.ui.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.Key_password;
import static com.example.bloodbank.data.local.SharedPreferncesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_EMAIL;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_ID;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_NAME;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_PHONE;
import static com.example.bloodbank.data.local.SharedPreferncesManger.setSharedPreferences;


public class ForgetPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.ForgetPasswordFragmentEditUserPhone)
    EditText ForgetPasswordFragmentEditUserPhone;

    @BindView(R.id.ForgetPasswordFragmentBtnSend)
    Button ForgetPasswordFragmentBtnSend;

    Unbinder unbinder;

    ApiServer apiServer;
    public static Login login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        // class login retrofit
        ClassLoginRetrofit();
        // Class All OnClick
        ClassAllOnClick();

        return view;
    }

    private void ClassAllOnClick() {




    }




    // class login retrofit
    private void ClassLoginRetrofit() {
        ForgetPasswordFragmentBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServer = getClient().create(ApiServer.class);
                Call<Reset> call = apiServer.resetPassword(ForgetPasswordFragmentEditUserPhone.getText().toString());
                call.enqueue(new Callback<Reset>() {
                    @Override
                    public void onResponse(Call<Reset> call, Response<Reset> response) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        if (response.body().getStatus() == 1) {



                            Bundle bundle=new Bundle();
                            bundle.putString("getPinCodeForTest",String.valueOf( response.body().getData().getPinCodeForTest()));
                            bundle.putString("UserPhone",ForgetPasswordFragmentEditUserPhone.getText().toString());
                            Fragment fragment = new NewPasswordFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragment.setArguments(bundle);
                            fragmentManager.beginTransaction().replace(R.id.splashActivityReplaceFragment, fragment).commit();

                        }
                    }

                    @Override
                    public void onFailure(Call<Reset> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}