package br.com.diademaenforma.enformaplus.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String msg) {
        super(msg);
    }
}
