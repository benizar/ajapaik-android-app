package ee.ajapaik.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import ee.ajapaik.android.fragment.ProfileFragment;
import ee.ajapaik.android.test.R;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class ProfileActivity extends NavigationDrawerActivity {

    public static String LAST_ACTIVITY = "lastActivity";

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, String lastActivity) {
        Intent intent = new Intent(context, ProfileActivity.class);
        if (lastActivity != null) {
            intent.putExtra(LAST_ACTIVITY, lastActivity);
        }
        intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        configureNavigationDrawer();
        configureToolbar();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ProfileFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isFromLoginActivity()) {
            this.startActivity(new Intent(this, NearestActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFromLoginActivity() {
        return "login".equals(getIntent().getStringExtra(LAST_ACTIVITY));
    }
}
