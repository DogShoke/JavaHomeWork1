import java.util.*;

abstract class Employee {
    protected String name;
    protected String position;
    protected double baseSalary; // базовый месячный оклад в у.е.

    public Employee(String name, String position, double baseSalary) {
        this.name = name;
        this.position = position;
        this.baseSalary = baseSalary;
    }

    public abstract double getMonthlySalary();

    @Override
    public String toString() {
        return String.format("%s (%s)", name, position);
    }
}
class Developer extends Employee {
    public enum Level { JUNIOR, MID, SENIOR }
    private Level level;
    private double experienceBonus;

    public Developer(String name, Level level, double baseSalary) {
        super(name, "Developer " + level.name(), baseSalary);
        this.level = level;
        this.experienceBonus = calculateExperienceBonus(level);
    }

    private double calculateExperienceBonus(Level level) {
        switch (level) {
            case JUNIOR: return 0.0;
            case MID:    return 300.0;
            case SENIOR: return 800.0;
            default:     return 0.0;
        }
    }

    @Override
    public double getMonthlySalary() {
        return baseSalary + experienceBonus;
    }
}

class Tester extends Employee {
    private boolean isAutomationEngineer;
    private double automationBonus;

    public Tester(String name, double baseSalary, boolean isAutomationEngineer) {
        super(name, "Tester" + (isAutomationEngineer ? " (Automation)" : ""), baseSalary);
        this.isAutomationEngineer = isAutomationEngineer;
        this.automationBonus = isAutomationEngineer ? 250.0 : 0.0;
    }

    @Override
    public double getMonthlySalary() {
        return baseSalary + automationBonus;
    }
}

class Manager extends Employee {
    private List<Employee> team;
    private double leadBonus;
    private double perMemberBonus;

    public Manager(String name, double baseSalary, double leadBonus, double perMemberBonus) {
        super(name, "Manager", baseSalary);
        this.team = new ArrayList<>();
        this.leadBonus = leadBonus;
        this.perMemberBonus = perMemberBonus;
    }

    public void addToTeam(Employee e) {
        if (e != null) team.add(e);
    }

    public List<Employee> getTeam() {
        return Collections.unmodifiableList(team);
    }

    @Override
    public double getMonthlySalary() {
        return baseSalary + leadBonus + perMemberBonus * team.size();
    }
}

public class Task2 {
    public static void main(String[] args) {
        List<Employee> allEmployees = new ArrayList<>();

        Manager mAlice = new Manager("Alice Johnson", 4000.0, 800.0, 150.0);
        Manager mBob = new Manager("Bob Petrov", 3500.0, 600.0, 120.0);

        Developer d1 = new Developer("Ivan Sidorov", Developer.Level.JUNIOR, 1200.0);
        Developer d2 = new Developer("Marina Kuznetsova", Developer.Level.MID, 2000.0);
        Developer d3 = new Developer("Sergey Ivanov", Developer.Level.SENIOR, 3500.0);
        Developer d4 = new Developer("Lena Smirnova", Developer.Level.MID, 2100.0);

        Tester t1 = new Tester("Oleg Morozov", 1400.0, false);
        Tester t2 = new Tester("Nina Volkova", 1700.0, true); // автоматизатор

        mAlice.addToTeam(d2);
        mAlice.addToTeam(d3);
        mAlice.addToTeam(t2);

        mBob.addToTeam(d1);
        mBob.addToTeam(d4);
        mBob.addToTeam(t1);

        allEmployees.add(mAlice);
        allEmployees.add(mBob);
        allEmployees.add(d1);
        allEmployees.add(d2);
        allEmployees.add(d3);
        allEmployees.add(d4);
        allEmployees.add(t1);
        allEmployees.add(t2);

        double totalPayroll = 0.0;
        System.out.println("Список сотрудников и месячные оклады");
        for (Employee e : allEmployees) {
            double salary = e.getMonthlySalary();
            totalPayroll += salary;
            System.out.printf("%-30s : %8.2f\n", e.toString(), salary);
        }
        System.out.println("-------------------------------------------");
        System.out.printf("Итого месячный фонд оплаты: %8.2f\n", totalPayroll);

        System.out.println("\nКоманды менеджеров и суммарная зарплата их команд");
        for (Employee e : Arrays.asList(mAlice, mBob)) {
            Manager m = (Manager) e;
            System.out.println(m.toString() + " — команда:");
            double teamSum = 0.0;
            for (Employee member : m.getTeam()) {
                double s = member.getMonthlySalary();
                teamSum += s;
                System.out.printf("   %-28s : %8.2f\n", member.toString(), s);
            }
            System.out.printf("   Сумма зарплат команды: %8.2f (плюс зарплата менеджера: %8.2f)\n",
                    teamSum, m.getMonthlySalary());
            System.out.println();
        }
    }
}