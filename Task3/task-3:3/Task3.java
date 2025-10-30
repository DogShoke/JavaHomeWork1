interface IProductPart { }

interface IProduct {
    void installFirstPart(IProductPart part);
    void installSecondPart(IProductPart part);
    void installThirdPart(IProductPart part);
}
interface ILineStep {
    IProductPart buildProductPart();
}

interface IAssemblyLine {
    IProduct assembleProduct(IProduct product);
}


class LaptopBody implements IProductPart {
    public LaptopBody() {
        System.out.println("Создан корпус ноутбука.");
    }
}

class LaptopMotherboard implements IProductPart {
    public LaptopMotherboard() {
        System.out.println("Создана материнская плата ноутбука.");
    }
}

class LaptopDisplay implements IProductPart {
    public LaptopDisplay() {
        System.out.println("Создан экран ноутбука.");
    }
}

class Laptop implements IProduct {
    private IProductPart body;
    private IProductPart motherboard;
    private IProductPart display;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part;
        System.out.println("Установлен корпус ноутбука.");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.motherboard = part;
        System.out.println("Установлена материнская плата ноутбука.");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.display = part;
        System.out.println("Установлен экран ноутбука.");
    }

    @Override
    public String toString() {
        return "Ноутбук собран: " +
                (body != null ? "Корпус ✓, " : "Корпус ✗, ") +
                (motherboard != null ? "Материнская плата ✓, " : "Материнская плата ✗, ") +
                (display != null ? "Экран ✓" : "Экран ✗");
    }
}

class BodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 1: создаем корпус...");
        return new LaptopBody();
    }
}

class MotherboardStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 2: создаем материнскую плату...");
        return new LaptopMotherboard();
    }
}

class DisplayStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 3: создаем экран...");
        return new LaptopDisplay();
    }
}

class LaptopAssemblyLine implements IAssemblyLine {
    private ILineStep step1;
    private ILineStep step2;
    private ILineStep step3;

    public LaptopAssemblyLine(ILineStep step1, ILineStep step2, ILineStep step3) {
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("\n=== Начинаем сборку ноутбука ===");
        IProductPart part1 = step1.buildProductPart();
        product.installFirstPart(part1);

        IProductPart part2 = step2.buildProductPart();
        product.installSecondPart(part2);

        IProductPart part3 = step3.buildProductPart();
        product.installThirdPart(part3);

        System.out.println("=== Сборка завершена ===\n");
        return product;
    }
}

public class Task3 {
    public static void main(String[] args) {
        // Создаем сборочные шаги
        ILineStep step1 = new BodyStep();
        ILineStep step2 = new MotherboardStep();
        ILineStep step3 = new DisplayStep();
        IAssemblyLine line = new LaptopAssemblyLine(step1, step2, step3);
        IProduct laptop = new Laptop();
        IProduct readyLaptop = line.assembleProduct(laptop);
        System.out.println(readyLaptop);
    }
}