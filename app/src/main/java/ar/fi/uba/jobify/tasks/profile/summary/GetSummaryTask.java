package ar.fi.uba.jobify.tasks.profile.summary;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import ar.fi.uba.jobify.activities.ProfileActivity;
import ar.fi.uba.jobify.domains.ProfileSummary;
import ar.fi.uba.jobify.exceptions.BusinessException;
import ar.fi.uba.jobify.tasks.AbstractTask;
import ar.fi.uba.jobify.utils.MyPreferenceHelper;
import ar.fi.uba.jobify.utils.MyPreferences;
import ar.fi.uba.jobify.utils.ShowMessage;
import fi.uba.ar.jobify.R;

/**
 * Created by smpiano on 9/28/16.
 */
public class GetSummaryTask extends AbstractTask<String,Void,ProfileSummary,ProfileActivity> {

    private final MyPreferenceHelper helper;
    private final MyPreferences pref;

    public GetSummaryTask(ProfileActivity activity) {
        super(activity);
        helper = new MyPreferenceHelper(activity.getApplicationContext());
        pref = new MyPreferences(activity.getApplicationContext());
    }

    @Override
    protected ProfileSummary doInBackground(String... params) {
        Context ctx = weakReference.get().getApplicationContext();
        String token = pref.get(ctx.getString(R.string.shared_pref_current_token),"");
        String email = params[0];
        ProfileSummary summary = null;
        try {
            summary = (ProfileSummary) restClient.get("/users/"+email+"/profile/summary?token="+token, withAuth(ctx));
        } catch (BusinessException e) {
            weakReference.get().showSnackbarSimpleMessage(e.getMessage());
        } catch (final Exception e) {
            weakReference.get().runOnUiThread(new Runnable() {
                public void run() {
                    ShowMessage.toastMessage(weakReference.get().getApplicationContext(), e.getMessage());
                }
            });
        }
        return summary;
    }

    @Override
    public Object readResponse(String json) throws JSONException {
        return ProfileSummary.fromJson(new JSONObject(json));
    }

    @Override
    protected void onPostExecute(ProfileSummary summary) {
        super.onPostExecute(summary);

        if(summary != null){
            ((ProfileRead) weakReference.get()).onProfileSummarySuccess(summary);
        } else{
            weakReference.get().showSnackbarSimpleMessage("No se puede obtener perfil summary");
        }
    }

    public interface ProfileRead {
        public void onProfileSummarySuccess(ProfileSummary summary);
    }

}
