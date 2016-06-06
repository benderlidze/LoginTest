package login.com.logintest.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
 * {@link SignIn.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignIn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Context mContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String[] data = {"one", "two", "three", "four", "five"};

    private String Name;
    private String Email;
    private String Phone;
    private String Spinner;

    private TextView name;
    private TextView email;
    private TextView phone;
    Spinner spinner;

    public SignIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignIn.
     */
    // TODO: Rename and change types and number of parameters
    public static SignIn newInstance(String param1, String param2) {
        SignIn fragment = new SignIn();
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
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);



        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        phone = (TextView) v.findViewById(R.id.phone);
        spinner = (Spinner) v.findViewById(R.id.spinner);

        setSpinner();

        Button signIn = (Button) v.findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkData()==true){
                    //sendind data to server
                    //Toast.makeText(mContext, "Sending data to server", Toast.LENGTH_SHORT).show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://m2.biz.ua/logintest.php")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitProcessData service = retrofit.create(RetrofitProcessData.class);
                    Call<String> call;
                    call = service.SignIn("signin",Name,Email,Phone,Spinner);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            Log.d("!!!RESP",""+ response.body());
                            String resp = response.body();
                            if (resp.equals("error_name")){
                                Toast.makeText(mContext, "Error! This name is already taken. Choose another one.", Toast.LENGTH_SHORT).show();
                            }else if (resp.equals("data_saved")){
                                // goto login poage
                                Toast.makeText(mContext, "Data saved.", Toast.LENGTH_SHORT).show();
                                mListener.onSigInSuccess("sss");
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("!!!RESP=",""+ t);
                        }
                    });

                }else{
                    //error fill all fields
                    Toast.makeText(mContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    protected boolean checkData(){
        Boolean checked = false;

        Name = name.getText().toString();
        Email = email.getText().toString();
        Phone = phone.getText().toString();

        if ((Name!=null && !Name.equals("")) &&
                (Email!=null && !Email.equals("")) &&
                (Phone!=null && !Phone.equals("")) ){
            checked = true;
        }else{
            //check name, email, phone
        }
        Log.d("!!!CheckData",""+checked);
        return checked;
    }

    protected void setSpinner (){

        // spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        // title
        spinner.setPrompt("Select something");
        // selecting element
        // spinner.setSelection(2);
        // setting the event
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // showing the possition of spiner
                //Toast.makeText(mContext, "Position = " + position, Toast.LENGTH_SHORT).show();
                Spinner = data[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.d("!!!BUtton pressed","2123");
        if (mListener != null) {
            mListener.onSigInSuccess("");
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
        void onSigInSuccess(String s);
    }
}
