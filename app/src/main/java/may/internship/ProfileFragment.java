package may.internship;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {

    Button logout,profile,changePassword,myOrder;
    SharedPreferences sp;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sp = getActivity().getSharedPreferences(ConstantData.PREF, Context.MODE_PRIVATE);

        changePassword = view.findViewById(R.id.fragment_profile_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(), ChangePasswordActivity.class);
            }
        });

        profile = view.findViewById(R.id.fragment_profile_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(), ProfileActivity.class);
            }
        });

        logout = view.findViewById(R.id.fragment_profile_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sp.edit().remove(ConstantData.CONTACT).commit();
                sp.edit().clear().commit();
                new CommonMethod(getActivity(),MainActivity.class);
            }
        });

        return view;
    }
}