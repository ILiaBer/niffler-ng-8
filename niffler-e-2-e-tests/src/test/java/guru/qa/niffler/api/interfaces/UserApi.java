package guru.qa.niffler.api.interfaces;

import guru.qa.niffler.model.users.UserJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import javax.annotation.Nullable;
import java.util.List;

public interface UserApi {

    @GET("internal/users/current")
    Call<UserJson> getUser(
            @Query("username") String username
    );

    @GET("internal/users/current")
    Call<UserJson> currentUser(@Query("username") String username);

    @GET("internal/users/all")
    Call<List<UserJson>> allUsers(@Query("username") String username,
                                  @Query("searchQuery") @Nullable String searchQuery);
}
