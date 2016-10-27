package br.edu.ifam.saf;


import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface SAFService {
    @GET("itens")
    Observable<Result<ItensResponse>> listarItems();

    @POST("usuarios")
    Observable<Result<Void>> registrarUsuario(@Body UsuarioDTO usuario);

    @GET("usuarios")
    Observable<Result<UsuariosResponse>> listarUsuarios();

    @POST("usuarios/login")
    Observable<Result<UsuarioDTO>> login(@Body LoginData loginData);
}
