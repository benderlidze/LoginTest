package login.com.logintest.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import login.com.logintest.R;
import login.com.logintest.Retrofit.RetrofitProcessData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Forget.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Forget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forget extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public Forget() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forget.
     */
    // TODO: Rename and change types and number of parameters
    public static Forget newInstance(String param1, String param2) {
        Forget fragment = new Forget();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_forget, container, false);


        Button forgetButton = (Button) view.findViewById(R.id.restorePasswordButton);
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView email = (TextView) view.findViewById(R.id.forgetEmail);
                String Email = email.getText().toString();

                if (!Email.equals("")){

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://m2.biz.ua/logintest.php")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitProcessData service = retrofit.create(RetrofitProcessData.class);
                    Call<String> call;
                    call = service.RestorePassword("forget", Email);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            Log.d("!!!RESP",""+ response.body());
                            String resp = response.body();

                            if (resp.equals("error_forget")){
                                Toast.makeText(mContext, "Error! Incorrect email address", Toast.LENGTH_SHORT).show();
                            }else if (resp.equals("success_forget")){
                                // goto login poage
                                Toast.makeText(mContext, "Check your email for your name(password)", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("!!!RESP=",""+ t);
                        }
                    });

                }else{
                    Toast.makeText(mContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String s) {
        if (mListener != null) {
            mListener.onForget(s);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onForget(String s);
    }
}
