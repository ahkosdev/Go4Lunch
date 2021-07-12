package fr.kosdev.go4lunch.network;

import fr.kosdev.go4lunch.model.autocomplete.AutocompleteResult;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbySearchApiCall {

    @GET("api/place/nearbysearch/json?key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
    Call<Example> getNearbyPlace(@Query("types") String type, @Query("location") String location, @Query("radius") int radius);

    @GET("api/place/details/json?key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
    Call<ExampleDetail> getPlaceId(@Query("place_id")  String placeId);

    @GET("api/place/autocomplete/json?key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE&types=establishment&radius=1500&strictbounds")
    Call<AutocompleteResult> getRestaurantsWithAutocomplete(@Query("input") String input, @Query("location") String location);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
