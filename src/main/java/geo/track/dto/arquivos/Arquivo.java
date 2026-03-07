package geo.track.dto.arquivos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.HashMap;

public class Arquivo {
    private final String nome;
    @Enumerated(EnumType.STRING)
    private final String formato;
    @Enumerated(EnumType.STRING)
    private final String template;
    private final HashMap<String, Object> dados;
    private final HashMap<String, String> metadados;
    private byte[] arquivo = null;

    public HashMap<String, String> obterMetadados() {
        return metadados;
    }

    public Boolean contemMetadado(String metadado, String valor) {
        boolean possuiMetadado = metadados.containsKey(metadado);

        if (possuiMetadado) return metadados.get(metadado).equalsIgnoreCase(valor);

        return false;
    }


    public String getNomeArquivo() {
        return nome+"."+formato;
    }

    public Boolean possuiArquivoCriado() {
        return !(arquivo == null);
    }

    public void definirArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getFormato() {
        return formato;
    }

    public String getTemplate() {
        return template;
    }

    public HashMap<String, Object> getDados() {
        return dados;
    }

    public HashMap<String, String> getMetadados() {
        return metadados;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public Arquivo(String nome, String formato, String template, HashMap<String, Object> dados, HashMap<String, String> metadados) {
        this.nome = nome;
        this.formato = formato;
        this.template = template;
        this.dados = dados;
        this.metadados = metadados;
    }
}