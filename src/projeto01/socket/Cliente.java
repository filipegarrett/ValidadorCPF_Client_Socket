
package projeto01.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.rodar();
    }

    public void rodar() {
        try {
            Socket socket = new Socket("127.0.0.1", 50000);
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Digite o CPF para validação: ");
            String cpf = br.readLine();

            saida.writeUTF(cpf);
            String resultado = entrada.readUTF();
            System.out.println(resultado);

            socket.close();
        } catch(Exception e) {
            rodar();
        }
    }
}
