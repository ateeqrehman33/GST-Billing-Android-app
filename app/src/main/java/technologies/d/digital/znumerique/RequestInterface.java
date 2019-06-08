package technologies.d.digital.znumerique;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("fp.php")
    Call<JSONResponse> getJSON();

}
