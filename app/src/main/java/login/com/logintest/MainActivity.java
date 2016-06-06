package login.com.logintest;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import login.com.logintest.Fragments.Forget;
import login.com.logintest.Fragments.LogIn;
import login.com.logintest.Fragments.ProtectedArea;
import login.com.logintest.Fragments.SignIn;

public class MainActivity extends AppCompatActivity
            implements SignIn.OnFragmentInteractionListener,
                        LogIn.OnFragmentInteractionListener,
        Forget.OnFragmentInteractionListener
            {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(f == null){
            //Add it, there is no Fragmen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SignIn.newInstance("",""),"SignIn")
                    .commit();
        }else{
            //It's already there
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        int id = item.getItemId();

        if (id == R.id.action_signin) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SignIn.newInstance("",""), "SignIn")
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        if (id == R.id.action_login) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, LogIn.newInstance("",""), "SignIn")
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        if (id == R.id.action_forget) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, Forget.newInstance("",""), "SignIn")
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
        //return false;
    }

    @Override
    public void onSigInSuccess(String s) {

        Log.d("!!!SENDING","DATA");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LogIn.newInstance("",""),"SignIn")
                .commit();

    }

    @Override
    public void onLoginSuccess(String s) {
        Log.d("!!!SENDING","DATA");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ProtectedArea.newInstance("",""),"SignIn")
                .commit();
    }

    @Override
    public void onForget(String s) {

    }
}
