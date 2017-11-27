package Url;

/**
 * Created by ASUS on 26-03-2017.
 * @author Frederico Balcao (a15307)
 */

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

import Threads.Threads;
import Pagina.Pagina;

/**
 * Classe Url que tem como atributos o host+get, o nivel, um hashmap de paginas e threads da classe Threads
 */
public class Url {
    //public int maxNivel;
    //private static String titleurl;
    private static String get;
    private static String host;
    private static int nivel = 0;
    protected int nrThreads = 0;
    protected static final Integer maxThreads = 10;

    /**
     * Array de Threads do tipo classe Threads com um maximo de threads inicializado
     */
    static Threads listaThreads[] = new Threads[maxThreads];

    /**
     * HashMap das Paginas
     */
    static LinkedHashMap<String, Pagina> paginaMap = new LinkedHashMap<String, Pagina>();

    /**
     * Metodo obterHTML que utiliza socket, faz a verificacao da posicao do host+get
     *
     * @param url
     * @return html
     */
    public synchronized String obterHTML(String url) {
        String html = "";
        try {
            /**
             * verificação da posição do host e get
             */
            int pos = url.indexOf("/");
            if (url.length() > 0) {
                String aux[];
                if (pos < 0) {
                    int pos2 = url.indexOf("?");
                    if (pos2 < 0) {
                        host = url;
                        get = "/";

                    } else {
                        aux = url.split("\\?", 2);
                        host = aux[0];
                        get = "/" + aux[1];
                    }

                } else {
                    aux = url.split("/", 2);
                    host = aux[0];
                    get = "/" + aux[1];
                }
            }

            /**
             * // HTTP request
             */
            InetAddress ipAddr;
            ipAddr = InetAddress.getByName(host);

            Socket sock = new Socket(ipAddr, 80);
            sock.setSoTimeout(100000);

            OutputStream os = sock.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);

            writer.println("GET " + get + " HTTP/1.1");
            writer.println("Host: " + host);
            writer.println("Connection: close");
            writer.println();

            /**
             * // HTTP response
             */
            InputStream is = sock.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while (true) {
                String line = br.readLine();
                if (line == null) break;

                html = html.concat(line);
            }
            sock.close();
        } catch (UnknownHostException e) {
            System.out.println("Host Desconhecido " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * Metodo que processa o html com utilizacao de duas classes especificas para tal tarefa
     * pelo pacote java.util.regex, sendo elas: java.util.regex.Pattern e java.util.regex.Matcher
     *
     * @param url
     */
    public synchronized void processarHTML(String url) {
        Pagina p;
        String titulo = "";
        String url_p = "";

        Pattern p_titulo = Pattern.compile("<title>(.*?)</title>");
        Matcher m_titulo = p_titulo.matcher(url);

        Pattern p_url = Pattern.compile("<a (.*?)href=\"(.*?)\"");
        Matcher m_url = p_url.matcher(url);

        if (m_titulo.find()) {
            titulo = m_titulo.group(1);
        }

        if (!paginaVisitada(host)) {
            p = new Pagina(host, nivel, titulo);
            inserePagMap(host, p); //metodo put que associa um valor a uma chave específica
        }
        nivel++;

        /**
         * Utilizacao do synchronized para a procura do url, bem como as verificacoes do mesmo
         */
        synchronized (this){
            while (m_url.find()) {
                url_p = m_url.group(2);

                if (url_p.equals("")) {
                } else if (url_p.startsWith("#")) {
                } else if (url_p.equals(" ")) {
                } else {
                    String aux[];
                    if (url_p.startsWith("https")) {
                        aux = url_p.split("https://");
                        url_p = aux[1];
                    }

                    if (url_p.startsWith("http")) {
                        aux = url_p.split("http://");
                        url_p = aux[1];
                    }
                    /**
                     * verifica se a pagina ja foi encontrada por algumas condicoes do url
                     */
                    if (!paginaVisitada(url_p)) {
                        if (url_p.startsWith("/")) {
                            if (url_p.startsWith("/css") || url_p.endsWith(".pdf") || url_p.endsWith(".png") || url_p.endsWith(".jpeg") || url_p.endsWith(".doc") || url_p.endsWith(".docx") || url_p.endsWith(".xls")) {
                            } else {
                                p = new Pagina(host + url_p, nivel, titulo);
                                inserePagMap(url_p, p);
                                processaThreads(nrThreads, host + url_p);
                            }
                        } else {
                            p = new Pagina(url_p, nivel, titulo);
                            inserePagMap(url_p, p);
                        }
                    }
                }
            }
        }
        nivel--;
    }

    /**
     * Metodo que lista os urls do mapa atraves metodo toString da classe Pagina que retorna o url e o titulo
     */
    public void listarURL() {
        /**
         *  o keyset retorna as chaves do mapa de paginas e faz print de cada pelo metodo toString da classe Pagina
         */
        for (String i : paginaMap.keySet()) {
            System.out.println(paginaMap.get(i).toString());
        }
    }

    /**
     * Metodo que insere a pagina no hashmap pela key e pelo valor
     * @param key
     * @param valor
     */
    protected synchronized void inserePagMap(String key, Pagina valor) {
        paginaMap.put(key, valor);
    }

    /**
     * Metodo que verifica se a pagina ja foi visitada, ou seja, se a mesma ja foi encontrada
     * @param pagina
     * @return
     */
    protected synchronized boolean paginaVisitada(String pagina) {
        String i;
        if (pagina.startsWith("www")) {
            i = "";
        } else {
            i = host;
        }
        return paginaMap.containsKey(i + pagina);
    }

    /**
     * Metodo que processa a execucao das threads pela classe Threads que executa o metodo run()
     * @param nrThreads
     * @param nome
     */
    synchronized void processaThreads(int nrThreads, String nome) {

        if (maxThreads.equals(nrThreads - 1)) {
            nrThreads++;
            for (int i = 0; i < nrThreads; i++) {
                try {
                    listaThreads[i].join();
                    listaThreads[i] = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        listaThreads[nrThreads] = new Threads(nome);
        listaThreads[nrThreads].start();
    }
}