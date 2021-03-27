package my.app.meraki;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SampleDesc extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Some example problems according to respective domains: ")
                .setMessage("1. Start with mentioning you're an individual or group/NGO. \n" +
                        "2. Mention your problem. \n" +
                        "Health: \n" +
                        "My name is Harvey. I am 34 years old having a fever and cough from the past week. I am a little worried cause these symptoms of mine match the Coronavirus ones and I want to get myself checked. Is there anyone who can advise me on this? \n" +
                        "\n" +
                        "My name is Sherlock. I work at an NGO for all the healthcare workers and we're running a shortage of masks. Is there any supplier out there who can help us with this problem? Let's connect. :') \n" +
                        "\n" +
                        "Animal Care : \n" +
                        "Hi, My name is Nick. I work at Animal healthcare and we're facing a shortage of (food for our service)/( people to provide services) \n" +
                        "Care to join ? \uD83D\uDC36\n" +
                        "\n" +
                        "Poor Care: \n" +
                        "Hi, my name is Sansa. I am currently running a food supply program for all the worker community and It would be kind of you if you could come forward and help us with this initiative. Let's get connected :)\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();

    }
}
