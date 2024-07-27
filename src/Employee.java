public class Employee {
private String name;
private int age;
private double salary;

    public Employee() {
    }

    public Employee(String name, int age,double salary) {
        this.salary = salary;
        this.name = name;
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return this.getName()+ " is "+this.getAge()+ " years old and earn "+ this.getSalary()+" $ per month";
    }
}
