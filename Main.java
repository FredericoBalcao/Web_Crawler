package Main;

/**
 * Created by ASUS on 26-03-2017.
 * @author Frederico Balcao (a15307)
 */

import Threads.Threads;
import Url.Url;

import static java.lang.System.exit;

/**
 * Sistemas Distribuídos 2016/2017
 * Trabalho 1 - Web Crawler
 * Escrever um programa de rastreador simples que pode analisar determinadas informacoes HTML
 * e descobrir os hiperlinks dentro do HTML.
 * - Conectar-se a um servidor web
 * - Enviar uma solicitação HTTP para o servidor
 * - Obter a resposta HTTP do servidor
 * - Processa-lo para encontrar mais URLs
 * - Repetir!
 * A solicitacao HTTP deve assumir forma: GET / HTTP / 1.1 Host: www.estgp.pt Conection: close
 * A linha em branco e necessaria!
 * Primeira linha contem documento / recurso para buscar
 * Para o documento raiz de um site, deve especificar / como caminho
 * Segunda linha especifica o nome do host do servidor web
 * (Varios hosts virtuais podem ser servidos a partir de um servidor fisico)
 * Terceira linha indica ao servidor para fechar a conexao quando a resposta é completamente enviada
 * Manipulacao de excecao no Web Crawler
 * Verificao se o tratamento de excecoes tem o nivel correto de granularidade
 * Operacoes para rastrear uma pagina da Web:
 * 1. Conecte-se ao servidor remoto com um soquete
 * 2. Envie a solicitação HTTP
 * 3. Leia novamente a resposta HTTP
 * 4. Analisar URLs do texto da resposta
 */


/**
 * Classe Main que executa o programa principal
 * Recebe como argumento o host (ex.www.estgp.pt) e chama os metodos da classe Url para obter o resultado desejado,
 * bem como a classe Threads para dar inicio das threads
 * Se nao receber qualquer argumento, sai do programa
 */
public class Main {
    public static void main(String argv[]){
        if (argv.length == 0) {
            System.out.println("Erro! Tem que inserir URL");
            exit(1);
        }

        Threads t = new Threads(argv[0]);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Url u = new Url();
        u.listarURL();
        exit(1);
    }
}