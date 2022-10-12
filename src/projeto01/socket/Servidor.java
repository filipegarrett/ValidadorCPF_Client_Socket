package projeto01.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        ServerSocket server = new ServerSocket(50000);
        Socket socket = server.accept();
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        String cpf = entrada.readUTF();
        String resultado;

        var retorno = servidor.validaCpf(cpf);
        if (retorno.get(0).equals(false)) {
            resultado = "O CPF informado é inválido -> " + retorno.get(1);
        } else {
            resultado = "O CPF informado " + cpf + " é válido -> " + retorno.get(1);
        }

        saida.writeUTF(resultado);
        socket.close();
    }

    public List<Object> validaCpf(String cpfString) {
        List<Object> retorno = new ArrayList<>();
        int digito1 = 0 , digito2 = 0, d1 = 0, d2 = 0, j = 0;
        int multiplicador = 2;
        String mensagem;
        Boolean validade = false;
        String cpf9digitos = cpfString.substring(0, cpfString.length() -2);

        if (cpfString.length() < 11) {
            mensagem = "O CPF informado tem menos de 11 caracteres";
            retorno.add(validade);
            retorno.add(mensagem);
            return retorno;
        }

        for (int i = 9; i > 0; i--) {
            int digitoCPF = Integer.parseInt(String.valueOf(cpf9digitos.charAt(i - 1)));
            digito1 = digito1 + ((multiplicador + j) * digitoCPF);
            j++;
        }

        if (digito1 % 11 < 2) {
            d1 = 0;
        } else {
            d1 = 11 - (digito1 % 11);
        }

        if (d1 != Integer.parseInt(String.valueOf(cpfString.charAt(9)))) {
            mensagem = "O CPF informado apresenta o dígito " + cpfString.charAt(9) + ", diferente do valor calculado: " + d1;
            retorno.add(validade);
            retorno.add(mensagem);
            return retorno;
        }
        j = 0;

        for (int s = 10; s > 0; s--) {
            int digitoCPF = Integer.parseInt(String.valueOf((cpf9digitos + d1).charAt(s - 1)));
            digito2 = digito2 + ((multiplicador + j) * digitoCPF);
            j++;
        }

        if (digito2 % 11 < 2) {
            d2 = 0;
        } else {
            d2 = 11 - (digito2 % 11);
        }

        if (d2 != Integer.parseInt(String.valueOf(cpfString.charAt(10)))) {
            mensagem = "O CPF informado apresenta o dígito " + cpfString.charAt(10) + ", diferente do valor calculado: " + d2;
            retorno.add(validade);
            retorno.add(mensagem);
            return retorno;
        }

        mensagem = "O CPF informado apresenta os dígitos " + cpfString.charAt(9) + cpfString.charAt(10) + ", iguais aos dígitos calculados: " + d1 + d2;
        validade = true;
        retorno.add(validade);
        retorno.add(mensagem);
        return retorno;
    }
}
