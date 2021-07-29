package fr.kosdev.go4lunch.utils;

import android.content.Context;

import fr.kosdev.go4lunch.BuildConfig;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo.OpeningHours;

public class Utils {

    public static String updateOpenHours(OpeningHours openingHours, Context context){
        String restaurantStatus = "";
        if (openingHours != null){
            if (openingHours.getOpenNow()== true){
                restaurantStatus = context.getResources().getString(R.string.open_text);
            }else {
                restaurantStatus = context.getResources().getString(R.string.close_text);
            }
        }
        return restaurantStatus;

    }
    public static float configureRatings(Double rating){
        int numberOfNumStars = 0;
        if (rating >1 && rating <= 2){
            numberOfNumStars = 1;
        }else if (rating > 2 && rating <= 3){
            numberOfNumStars = 2;
        }else if (rating > 4){
            numberOfNumStars = 3;
        }
        return numberOfNumStars;

    }

    public static String getMyLocation(Double latitude, Double longitude ){
        String myLocation = latitude + "," + longitude;
        return myLocation;

    }

    public static String getPhotoUrl(String photoReference) {
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=600&photoreference=" + photoReference + "&key=" + BuildConfig.GOOGLE_API_KEY;
        return url;
    }

    public static String getWorkmateInfo(Workmate workmate, Context context){
        String workmateInfo = workmate.getFirstname() + " " + context.getResources().getString(R.string.choice_text) + " "+ "("+(workmate.getRestaurantName())+")";
        return workmateInfo;
    }

    public static String getWorkmateDetailInfo(Workmate workmate, Context context){
        String workmateDetailInfo = workmate.getFirstname() + " " + context.getResources().getString(R.string.joining_text);
        return workmateDetailInfo;
    }
}
