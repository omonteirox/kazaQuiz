package ifgoiano.kazaquiz;

public class modeloQuestao {
    String Pergunta;
    String A,B,C,D,Resposta;

    public modeloQuestao(String pergunta, String a, String b, String c, String d, String resposta) {
        Pergunta = pergunta;
        A = a;
        B = b;
        C = c;
        D = d;
        Resposta = resposta;
    }
    public modeloQuestao(){};

    public String getPergunta() {
        return Pergunta;
    }

    public void setPergunta(String pergunta) {
        Pergunta = pergunta;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getResposta() {
        return Resposta;
    }

    public void setResposta(String resposta) {
        Resposta = resposta;
    }
}
