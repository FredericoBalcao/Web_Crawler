package Threads;

/**
 * Created by ASUS on 26-03-2017.
 * @author Frederico Balcao (a15307)
 */

import Url.Url;

/**
 * Classe Threads que executa o metodo run() para processar cada thread
 */
public class Threads extends Thread{
    private String nomeDaThread;

    /**
     * Metodo construtor da classe Threads que inicializa a string com o nome da thread
     * @param nome
     */
    public Threads(String nome){
          this.nomeDaThread = nome;
    }

    public void run() {
        System.out.println("Nome da Thread = " + nomeDaThread);
        Url u = new Url();
        u.processarHTML(u.obterHTML(nomeDaThread));
    }

}