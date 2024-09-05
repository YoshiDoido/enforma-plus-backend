package br.com.diademaenforma.enformaplus.entity;

public enum Papel {
    ADMINISTRADOR("administrador"),
    USUARIO_COMUM("usuario_comum"),
    USUARIO_PROFISSIONAL("usuario_profissional");

    private String papel;

    Papel(String papel) {
        this.papel = papel;
    }

    public String getPapel() {
        return papel;
    }
}
