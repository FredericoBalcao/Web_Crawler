package Pagina;

/**
 * Created by ASUS on 26-03-2017.
 * @author Frederico Balcao (a15307)
 */

import java.lang.*;

/**
 * Classe Pagina que tem como atributos um url e um titulo do tipo String, e um nivel do tipo inteiro
 * Uso de metodos set e get para os atributos, bem como um metodo toString que retorna o ouput dos atributos definidos
 */
public class Pagina {
    private String url;
    public int nivel;
    public String titulo;

    /**
     * Metodo construtor que recebe como parametro um url, um nivel e um titulo
     * @param url
     * @param nivel
     * @param titulo
     */
    public Pagina(String url, int nivel, String titulo) {
        this.url = url;
        this.nivel = nivel;
        this.titulo = titulo;
    }

    /**
     * Metodo get do url que retorna o url do tipo String
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Metodo set do url que inicializa a variavel, recebe como argumento um titulo do tipo string
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Metodo set do titulo que inicializa a variavel, recebe como argumento um titulo do tipo string
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Metodo get do titulo que retorna o titulo definido
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Metodo get do nivel que retorna o nivel definido
     * @return nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Metodo set do nivel que inicializa a variavel, recebe como argumento um nivel do tipo inteiro
     * @param nivel
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Metodo que retorna o ouput desejado na consola, o url e o titulo.
     * Alem destes atributos, foi tambem implementado uma variavel espaco que define um output de espacos baseados em (nivel atual * 3)
     * por uma variavel espaco ("") que e concatenada (" ")
     * ou seja, se o nivel atual for 1 o output sera 3*1 = 3, mostra 3 espacos (" "):   |   url+titulo)
     * se o nivel atual for 2 o output sera 3*2 = 6, mostra 6 espacos (" "):            |      url+titulo)
     * se o nivel atual for 3 o output sera 3*3 = 9, mostra 9 espacos (" "):            |         url+titulo)
     * e assim sucessivamente consoante o nivel atual
     * @return espaco, url e titulo
     */
    public String toString() {
        String espaco = "";
        for (int i = 0; i < this.getNivel() * 3; i++) {
            espaco = espaco.concat(" ");
        }
        return (espaco + this.url + /*" Nivel: " + this.getNivel() +*/ " (" + this.getTitulo() + ")");
    }
}