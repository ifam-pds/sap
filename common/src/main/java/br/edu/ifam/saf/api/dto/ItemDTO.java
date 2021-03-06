package br.edu.ifam.saf.api.dto;

import br.edu.ifam.saf.enums.StatusItem;

public class ItemDTO {
    private Integer id;
    private String nome;
    private Double precoPorHora;
    private String descricao;
    private String marca;
    private String modelo;
    private String imagem;
    private CategoriaDTO categoria;
    private StatusItem status;

    public ItemDTO() {
    }

    public ItemDTO(Integer id, String nome, Double precoPorHora, String descricao, String marca, String modelo, String imagem, CategoriaDTO categoria, StatusItem status) {
        this.id = id;
        this.nome = nome;
        this.precoPorHora = precoPorHora;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.imagem = imagem;
        this.categoria = categoria;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusItem getStatus() {
        return status;
    }

    public void setStatus(StatusItem status) {
        this.status = status;
    }

    public Double getPrecoPorHora() {
        return precoPorHora;
    }

    public void setPrecoPorHora(Double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Double getPrecoPorMinuto() {
        return precoPorHora / 60;
    }

    public static class Builder {
        private Integer id;
        private String nome;
        private Double precoPorHora;
        private String descricao;
        private String marca;
        private String modelo;
        private String imagem;
        private CategoriaDTO categoria;
        private StatusItem status;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder imagem(String imagem) {
            this.imagem = imagem;
            return this;
        }

        public Builder precoPorHora(Double precoPorHora) {
            this.precoPorHora = precoPorHora;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder categoria(CategoriaDTO categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder status(StatusItem status) {
            this.status = status;
            return this;
        }

        public ItemDTO build() {
            return new ItemDTO(id, nome, precoPorHora, descricao, marca, modelo, imagem, categoria, status);
        }
    }

}
