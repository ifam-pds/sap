package br.edu.ifam.saf.criarconta;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

class CriarContaPresenter implements CriarContaContract.Presenter {


    private CriarContaContract.View view;

    public CriarContaPresenter(CriarContaContract.View view) {
        this.view = view;
    }

    @Override
    public void registrar(UsuarioDTO usuarioDTO) {
        SAFService apiService = ApiManager.getService();
        apiService.registrarUsuario(usuarioDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<Void>>() {
                    @Override
                    public void call(Result<Void> result) {
                        if (!result.response().isSuccessful()) {
                            MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                            view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
                        } else {
                            view.mostrarMensagemContaCriada();
                        }
                    }
                })


        ;

    }

    @Override
    public void destroy() {
        view = null;
    }
}