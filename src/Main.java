import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final int NAME_SIZE = 50;
    public static final int RECORD_SIZE = 112;
    //  50*2 + 4 + 8

    public static void main(String[] args) {

        Employee[] employees = new Employee[3];
        employees[0] = new Employee("test1", 20, 1000.500);
        employees[1] = new Employee("test2", 30, 2500.500);
        employees[2] = new Employee("test3", 35, 3200.500);

// write in random access file
        writeInFile(employees);

//  read from random access file
        readInFile();
    }

    private static void readInFile() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("randomAccess.dat", "r");) {
            int n = (int) randomAccessFile.length() / RECORD_SIZE;
            Employee[] readEmployees = new Employee[n];
            for (int i = n - 1; i >= 0; i--) {
                randomAccessFile.seek((long) i * RECORD_SIZE);
                readEmployees[n - i - 1] = readData(randomAccessFile);
            }
            randomAccessFile.close();
            System.out.println("Loaded data from file: ");
            for (Employee e : readEmployees)
                System.out.println(e);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    private static void writeInFile(Employee[] employees) {
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("randomAccess.dat"));
            for (Employee e : employees)
                writeData(outputStream, e);
            outputStream.close();
            System.out.println("Data stored in file.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Employee readData(RandomAccessFile randomAccessFile) throws IOException {
        Employee employee = new Employee();
        employee.setName(readFixedString(randomAccessFile));
        employee.setAge(randomAccessFile.readInt());
        employee.setSalary(randomAccessFile.readDouble());
        return employee;
    }

    private static String readFixedString(RandomAccessFile randomAccessFile) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        boolean more = true;
        while (more && i < Main.NAME_SIZE) {
            char ch = randomAccessFile.readChar();
            if (ch == 0)
                more = false;
            else stringBuilder.append(ch);
            i++;
        }
        randomAccessFile.skipBytes(2 * (Main.NAME_SIZE - i));
        return stringBuilder.toString();
    }

    private static void writeData(DataOutputStream outputStream, Employee e) throws IOException {
        writeFixedString(e.getName(), outputStream);
        outputStream.writeInt(e.getAge());
        outputStream.writeDouble(e.getSalary());
    }

    private static void writeFixedString(String str, DataOutputStream outputStream) throws IOException {
        int ch = 0;
        for (int i = 0; i < Main.NAME_SIZE; i++) {
            ch = 0;
            if (i < str.length()) ch = str.charAt(i);
            outputStream.writeChar(ch);

        }
    }
}
