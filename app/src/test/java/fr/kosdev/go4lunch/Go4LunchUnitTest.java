package fr.kosdev.go4lunch;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.kosdev.go4lunch.model.pojo.OpeningHours;
import fr.kosdev.go4lunch.utils.Utils;

import static android.os.Build.VERSION_CODES.Q;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Q)
public class Go4LunchUnitTest {
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void openingHoursTest() {
        OpeningHours openingHours = new OpeningHours();
        openingHours.setOpenNow(true);
        assertEquals(context.getString(R.string.open_text), Utils.updateOpenHours(openingHours, context));
        openingHours.setOpenNow(false);
        assertEquals(context.getString(R.string.close_text), Utils.updateOpenHours(openingHours, context));
    }
}