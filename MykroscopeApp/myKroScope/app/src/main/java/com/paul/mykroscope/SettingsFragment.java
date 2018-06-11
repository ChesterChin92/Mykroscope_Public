package com.paul.mykroscope;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SettingsFragment extends Fragment {
//    ImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);


    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        TextView github = (TextView) view.findViewById(R.id.githubText);

        github.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "";
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        });
        TextView website = (TextView) view.findViewById(R.id.websiteText);

        website.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "";
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        });
        TextView contact = (TextView) view.findViewById(R.id.contactUsText);

        contact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.setData(Uri.parse(""));

                /* Send it off to the Activity-Chooser */
                startActivity(emailIntent);
            }
        });
//        profileImage = view.findViewById(R.id.profileImage);
//        Button logout_button = (Button) view.findViewById(R.id.logout_button);
//        logout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut(v);
//            }
//        });
//        GraphRequest request = GraphRequest.newGraphPathRequest(
//                accessToken,
//                "/" + Profile.getCurrentProfile().getId() + "/picture",
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        JSONArray jsonArray = null;
//                        try {
//                          jsonArray = response.getJSONObject().getJSONArray("data");
//                            for(int i=0; i < jsonArray.length(); i++){
//                                JSONObject page = jsonArray.getJSONObject(i);
//                                String imageURL = page.getString("url");
//                                Log.d(getActivity().getClass().getSimpleName(), imageURL);
//                                Uri imageUri = Uri.parse(imageURL);
//                                profileImage.setImageURI(imageUri);
//                            }
//
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                            Log.d(getActivity().getClass().getSimpleName(), e.toString());
//                        }
//
//
//
//
//
////                        JSONObject result = response.getJSONArray('data');
////                        String res = (String) result.get('url');
////                        Log.d(getActivity().getClass().getSimpleName(),result.get("url"));
////                        Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG);
////                        Intent i = new Intent("android.intent.action.MAIN");
////                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
////                        i.addCategory("android.intent.category.LAUNCHER");
////                        i.setData(Uri.parse(url));
////                        startActivity(i);
//                    }
//                });
//        request.executeAsync();
    }
    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        getActivity().finish();
//        v.findViewById(R.id.login_button).setVisibility(View.VISIBLE);
    }

}
