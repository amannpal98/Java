class ClassA {
    private String name;
    private int value;

    public ClassA(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public ClassA(ClassA other) {
        this.name = other.name;
        this.value = other.value;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return value;
    }
}

class ClassB extends ClassA {
    protected String description;

    public ClassB(String name, int value, String description) {
        super(name, value);
        this.description = description;
    }

    public ClassB(ClassB other) {
        super(other);
        this.description = other.description;
    }

    public String getCourse() {
        return description;
    }
}

class ClassC extends ClassB {
    private String address;

    private ClassC(String name, int age, String course, String address) {
        super(name, age, course);
        this.address = address;
    }

    public ClassC(ClassC other) {
        super(other);
        this.address = other.address;
    }

    public static ClassC createInstance(String name, int age, String course, String address) {
        return new ClassC(name, age, course, address);
    }

    public synchronized void displayInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Course: " + getCourse());
        System.out.println("Address: " + address);
    }
}

public class multiClassProgram {
    public static void main(String[] args) {
        
        // variables in ClassA from ClassC (use getter/setter methods)
        ClassC objC = ClassC.createInstance("Aman Pal", 20, "BCA Student", "Jaunpur UP");
        objC.displayInfo();

        //Object creation of ClassB using a private constructor.
        ClassC objC2 = new ClassC(objC);
        objC2.displayInfo();
    }
}