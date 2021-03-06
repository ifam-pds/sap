package br.edu.ifam.saf.requisitarreserva;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import br.edu.ifam.saf.MainApplication;
import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.util.DinheiroFormatter;
import br.edu.ifam.saf.util.TimeFormatter;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservaActivity extends AppCompatActivity implements ReservaContract.View {
    public static final String EXTRA_ITEM_ID = "item_id";


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tempo_reserva)
    FieldView tempoReserva;

    @BindView(R.id.valor_total)
    FieldView valorTotal;

    @BindView(R.id.nome_item)
    TextView nomeItem;

    @BindView(R.id.marca_item)
    TextView marcaItem;


    @BindView(R.id.descricao_item)
    TextView descricaoItem;

    @BindView(R.id.form)
    View formContainer;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ReservaContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserva);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ReservaPresenter(this, ApiManager.getService(), MainApplication.getRepository());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.salvarReserva(TimeFormatter.parseToMinutes(tempoReserva.getText()));
            }
        });


        fab.setEnabled(false);
        formContainer.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int itemId = extras.getInt(EXTRA_ITEM_ID);
            presenter.carregarItem(itemId);
        }

        tempoReserva.addTextChangeListener(new FieldView.TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().trim().isEmpty()) {
                    presenter.onTempoChanged(TimeFormatter.parseToMinutes(s.toString()));

                } else {
                    valorTotal.setText("");
                }

            }
        });
        appBar.setBackground(getDrawable(R.drawable.bp_material_button_background));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return false;
    }

    @Override
    public void mostrarDetalhesItem(ItemAluguelDTO itemAluguelDTO) {
        esconderLoading();

        fab.setEnabled(true);
        formContainer.setVisibility(View.VISIBLE);

        nomeItem.setText(itemAluguelDTO.getItem().getNome());
        marcaItem.setText(itemAluguelDTO.getItem().getMarca() + "(" + itemAluguelDTO.getItem().getModelo() + ")");
        tempoReserva.setText(TimeFormatter.format(itemAluguelDTO.getDuracaoEmMinutos()));
        descricaoItem.setText(itemAluguelDTO.getItem().getDescricao());

        atualizarTotal(itemAluguelDTO.calcularTotal());

        Glide.with(this).load(MainApplication.getRepository().getImagePath() + itemAluguelDTO.getItem().getImagem())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder)

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(getResources(), resource);
                        appBar.setBackground(drawable);
                    }
                })
        ;


    }

    @Override
    public void fechar() {
        finish();
    }

    @Override
    public void atualizarTotal(double valor) {
        valorTotal.setText(DinheiroFormatter.format(valor));
    }

    @Override
    public void mostrarLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void esconderLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void mostrarInfoMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
