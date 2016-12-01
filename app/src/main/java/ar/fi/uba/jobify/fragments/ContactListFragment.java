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

        //inflo la vista de listado de elementos
        View fragmentView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ListView contactsList = (ListView) fragmentView.findViewById(R.id.contactListView);

        //Defino el adapter
        ContactListAdapter contactsAdapter = new ContactListAdapter(
                getActivity(),
                getContext(),
                R.layout.list_professional_item,
                new ArrayList<ProfessionalSearchItem>());
        //Asocio la listView con el adapter
        contactsList.setAdapter(contactsAdapter);
        contactsList.setOnItemClickListener(this);

        // Si tarda mucho debería mostrar una barra de progreso
        ProgressBar bar= new ProgressBar(getContext());
        bar.setIndeterminate(true);
        contactsList.setEmptyView(bar);
        contactsAdapter.refresh();
        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Cuando selecciono un item
        ProfessionalSearchItem professionalSearchItem = (ProfessionalSearchItem)parent.getItemAtPosition(position);

        //Lo envío al perfil
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra(Intent.EXTRA_UID, professionalSearchItem.getEmail());
        startActivity(intent);
    }
}
