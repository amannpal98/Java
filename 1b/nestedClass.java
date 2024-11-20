final class Utility {
    public static void printMessage(String message) {
        System.out.println(message);
    }
}

class OuterClass {
    private static int staticVar = 10;
    private int instanceVar;

    public OuterClass(int instanceVar) {
        this.instanceVar = instanceVar;
    }

    public static class StaticNestedClass {
        private static String nestedStaticVar = "Static Variable in Nested Class";
        private String nestedInstanceVar;

        static {
            System.out.println("Static block in StaticNestedClass");
        }

        {
            System.out.println("Instance block in StaticNestedClass");
        }

        public StaticNestedClass(String nestedInstanceVar) {
            this.nestedInstanceVar = nestedInstanceVar;
            System.out.println("Constructor in StaticNestedClass");
        }

        public void accessOuterClass() {
            System.out.println("Accessing OuterClass staticVar: " + OuterClass.staticVar);
        }
    }

    public final class FinalInnerClass {
        private final String finalVar = "Final Variable in Inner Class";

        public FinalInnerClass() {
            System.out.println("Constructor in FinalInnerClass");
        }

        public final void displayFinalVar() {
            System.out.println("Final variable: " + finalVar);
        }
    }

    @Override
    public String toString() {
        return "OuterClass instanceVar: " + instanceVar;
    }

    public static void accessStaticVar() {
        System.out.println("Accessing static variable from static method: " + staticVar);
    }

    public synchronized void accessSharedStaticVar() {
        staticVar++;
        System.out.println("Accessing shared static variable in synchronized method: " + staticVar);
    }

    public void restrictedAccess() {
    }
}

public class nestedClass {
    public static void main(String[] args) {
        Utility.printMessage("Creating instance of OuterClass");
        OuterClass outerClass = new OuterClass(20);
        System.out.println(outerClass);

        OuterClass.StaticNestedClass nestedClass = new OuterClass.StaticNestedClass("Nested Instance Variable");
        nestedClass.accessOuterClass();

        Utility.printMessage("Creating instance of FinalInnerClass");
        OuterClass.FinalInnerClass finalInnerClass = outerClass.new FinalInnerClass();
        finalInnerClass.displayFinalVar();

        Utility.printMessage("Accessing shared static variable");
        outerClass.accessSharedStaticVar();

        Utility.printMessage("Accessing static variable");
        OuterClass.accessStaticVar();

        Utility.printMessage("Attempting restricted access to instance variable");
        outerClass.restrictedAccess();
    }
}