import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Transfer {
    private static Transfer transfer;

    public static final String PEPPER = "Q,p83j";
    private final SocketAddress address;
    private DatagramSocket server;
    private String userLogin;
    private String userPassword;

    static File file = new File("trans.ser");

    private Transfer() {
        try {
            server = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Ошибка создания сокета");
            exit(0);
        }
        InetAddress inetAddress  = null;
        try {
            inetAddress = InetAddress.getByName(MyConstant.HOST);
        } catch (UnknownHostException e) {
            System.out.println("Ошибка определения адреса");
            exit(0);
        }
        address = new InetSocketAddress(inetAddress, MyConstant.PORT);
        try {
            server.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static Transfer getTransfer() {
        if (transfer == null) {
            transfer = new Transfer();
        }
        return transfer;
    }

    public Request Start(String input, LabWork labWork) throws IOException, ClassNotFoundException {
        int mistake = 0;
        String[] current = input.split(" ");

        if (current.length == 0)
            current = new String[]{" "};

        Request answer = null;
        Request request = null;

        switch (current[0]) {
            case ("get"):
                while (mistake < 4) {
                    request = new Request("help");
                    request.setInfo(userLogin, userPassword);
                    request.setGetCollection(true);
                    sendLetter(request);
                    answer = getLetter(server);
                    if (answer == null)
                        mistake++;
                    else
                        break;
                }
                break;
            case ("exit"):
                System.exit(0);
                break;
            case ("help"):
            case ("show"):
            case ("info"):
            case ("history"):
            case ("clear"):
            case ("sum_of_minimal_point"):
            case ("max_by_name"):
                while (mistake < 4) {
                    request = new Request(current[0]);
                    request.setInfo(userLogin, userPassword);
                    sendLetter(request);
                    answer = getLetter(server);
                    if (answer == null)
                        mistake++;
                    else
                        break;
                }
                break;
            case ("insert"):
            case ("replace_if_greater"):
            case ("update"):
                if (current.length < 2) {
                    System.out.println("Не введен аргумент");
                    return null;
                }
                while (mistake < 4) {
                    request = new Request(current[0], current[1], labWork);
                    request.setInfo(userLogin, userPassword);
                    sendLetter(request);
                    answer = getLetter(server);
                    if (answer == null)
                        mistake++;
                    else
                        break;
                }
                break;
            case ("remove_key"):
            case ("remove_lower_key"):
            case ("count_by_minimal_point"):
            case ("execute_script"):
                if (current.length < 2) {
                    System.out.println("Не введен аргумент");
                    return null;
                }
                while (mistake < 4) {
                    request = new Request(current[0], current[1]);
                    request.setInfo(userLogin, userPassword);
                    sendLetter(request);
                    answer = getLetter(server);
                    if (answer == null)
                        mistake++;
                    else
                        break;
                }
                break;
            default:
                System.out.println("Неправильно введена команда");
                return null;
        }
        return answer;
    }

    public boolean authorization(String input, String login, String password) {
        int mistake = 0;

        Request answer = null;
        switch (input) {
            case ("signup"):
            case ("login"):
                while (mistake < 4) {
                    sendLetter(new Request(input, login, Useful.generatePassword(password, PEPPER)));
                    answer = getLetter(server);
                    if (answer == null)
                        mistake++;
                    else
                        break;
                }
                if (answer == null) {
                    System.exit(0);
                }
                //output(answer.getAnswer());
                if (answer.isTrigger()) {
                    userLogin = answer.getLogin();
                    userPassword = answer.getPassword();
                    return true;
                }
                break;
            default:
                System.out.println("Неправильная команда");
        }
        return false;
    }

    public void sendLetter(Request send) {
        byte[] request;
        FileOutputStream fileOutput;
        FileInputStream fileInput;
        ObjectOutputStream objectOut;

        try {
            fileOutput = new FileOutputStream(file);
            objectOut = new ObjectOutputStream(fileOutput);

            objectOut.writeObject(send);

            fileInput = new FileInputStream(file);

            request = new byte[(int)file.length()];
            fileInput.read(request, 0, request.length);


            /*byte[] letterSize = Useful.convertToByte(request.length);
            DatagramPacket i = new DatagramPacket(letterSize, MyConstant.SIZE, address);
            server.send(i);*/

            DatagramPacket o = new DatagramPacket(request, request.length, address);
            server.send(o);

            objectOut.close();
            fileOutput.close();
            fileInput.close();
        } catch (Exception e) {
            System.out.println("Проблема с файлом");
        }
    }

    public Request getLetter(DatagramSocket datagramSocket) {
        FileOutputStream fileOutput;
        FileInputStream fileInput;
        ObjectInputStream objectInput;

        byte[] length = new byte[MyConstant.SIZE];
        DatagramPacket letterSize = new DatagramPacket(length, length.length);

        try {
            datagramSocket.receive(letterSize);

            byte[] req = new byte[Useful.convertToInt(length)];
            DatagramPacket inputRequest = new DatagramPacket(req, req.length);
            datagramSocket.receive(inputRequest);

            fileOutput = new FileOutputStream(file);

            fileOutput.write(req);

            fileInput = new FileInputStream(file);
            objectInput = new ObjectInputStream(fileInput);

            Request request = (Request) objectInput.readObject();

            objectInput.close();
            fileOutput.close();
            fileInput.close();

            return request;
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Сервер не отвечает");
            return null;
        }
    }

    public void output(ArrayList<String> output) {
        for (String u: output)
            System.out.println(u);
    }
}
