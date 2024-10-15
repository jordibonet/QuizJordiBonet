package players;

public class Jugador {

    String userName;
    String date_time;
    int respuestasCorrectas;
    int respuestasIncorrectas;

    public Jugador(String userName, String date_time, int respuestasCorrectas, int respuestasIncorrectas) {
        this.userName = userName;
        this.date_time = date_time;
        this.respuestasCorrectas = respuestasCorrectas;
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public void setRespuestasCorrectas(int respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public int getRespuestasIncorrectas() {
        return respuestasIncorrectas;
    }

    public void setRespuestasIncorrectas(int respuestasIncorrectas) {
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "userName='" + userName + '\'' +
                ", date_time='" + date_time + '\'' +
                ", respuestasCorrectas=" + respuestasCorrectas +
                ", respuestasIncorrectas=" + respuestasIncorrectas +
                '}';
    }

    public String file() {
        return userName+";"+date_time+";"+respuestasCorrectas+";"+respuestasIncorrectas;
    }
}
