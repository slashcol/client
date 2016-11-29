package ar.fi.uba.jobify.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ar.fi.uba.jobify.activities.ProfileActivity;
import ar.fi.uba.jobify.adapters.ContactListAdapter;
import ar.fi.uba.jobify.domains.Contact;
import ar.fi.uba.jobify.domains.ProfessionalSearchItem;
import fi.uba.ar.jobify.R;

/**
 * Created by smpiano on 9/28/16.
 */
public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public ContactListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView= inflater.inflate(R.layout.fragment_contact_list, container, false);
        ListView clientsList= (ListView)fragmentView.findViewById(R.id.contactListView);

        ContactListAdapter clientsAdapter = new ContactListAdapter( getActivity(), getContext(), R.layout.list_professional_item, new ArrayList<ProfessionalSearchItem>());
        clientsList.setAdapter(clientsAdapter);
        clientsList.setOnItemClickListener(this);

        ProgressBar bar= new ProgressBar(getContext());
        bar.setIndeterminate(true);
        clientsList.setEmptyView(bar);
        clientsAdapter.refresh();
        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact)parent.getItemAtPosition(position);
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra(Intent.EXTRA_UID, contact.getId());
        startActivity(intent);
    }
}
