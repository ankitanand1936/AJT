package com.learnforge.config;

import com.learnforge.entity.CourseModule;
import com.learnforge.entity.Lesson;
import com.learnforge.entity.Question;
import com.learnforge.entity.Role;
import com.learnforge.entity.User;
import com.learnforge.repository.CourseModuleRepository;
import com.learnforge.repository.RoleRepository;
import com.learnforge.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Runs at start-up: creates the two roles, the default admin account and
 * a small amount of sample Java content (only when the tables are empty).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    public static final String ADMIN_EMAIL = "admin@learnforge.com";
    public static final String ADMIN_PASSWORD = "Admin@123";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CourseModuleRepository moduleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           CourseModuleRepository moduleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));
        roleRepository.findByName("STUDENT")
                .orElseGet(() -> roleRepository.save(new Role("STUDENT")));

        if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
            User admin = new User();
            admin.setFullName("LearnForge Admin");
            admin.setEmail(ADMIN_EMAIL);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRole(adminRole);
            userRepository.save(admin);
        }

        if (moduleRepository.count() == 0) {
            createSampleContent();
        }
    }

    private void createSampleContent() {
        // ---------------- Module 1 ----------------
        CourseModule basics = new CourseModule("Java Fundamentals",
                "Start here: how Java works and how to store data in variables.");

        Lesson intro = addLesson(basics, "Introduction to Java and the JVM", 1, """
                Java is a high-level, object-oriented programming language released by Sun Microsystems in 1995. \
                Its famous promise is "write once, run anywhere".

                How a Java program runs:
                1. You write source code in a .java file.
                2. The compiler (javac) translates it into bytecode, stored in a .class file.
                3. The Java Virtual Machine (JVM) executes the bytecode on any operating system.

                The JDK (Java Development Kit) contains the tools to develop programs, while the JRE \
                (Java Runtime Environment) contains what is needed to run them.

                A minimal program:

                public class Hello {
                    public static void main(String[] args) {
                        System.out.println("Hello, LearnForge!");
                    }
                }

                Execution always starts in the main method.""");
        addQuestion(intro, "Which tool compiles Java source code into bytecode?",
                "java", "javac", "javadoc", "jar", "B");
        addQuestion(intro, "What does the JVM execute?",
                "Source code (.java files)", "HTML pages", "Bytecode (.class files)", "SQL scripts", "C");
        addQuestion(intro, "Which method is the starting point of a Java program?",
                "public static void main(String[] args)", "void start()", "public void run()", "static int init()", "A");

        Lesson types = addLesson(basics, "Variables and Data Types", 2, """
                A variable is a named memory location that stores a value. Java is statically typed, \
                so every variable must be declared with a type.

                The eight primitive types:
                - byte, short, int, long  (whole numbers)
                - float, double           (decimal numbers)
                - char                    (a single character)
                - boolean                 (true or false)

                String is not a primitive; it is a class that stores text.

                Examples:

                int age = 20;
                double price = 99.5;
                char grade = 'A';
                boolean passed = true;
                String name = "Asha";
                final double PI = 3.14159;   // final makes it a constant

                A decimal literal such as 3.14 is a double by default; write 3.14f to get a float.""");
        addQuestion(types, "Which data type stores only true or false?",
                "int", "String", "boolean", "char", "C");
        addQuestion(types, "Which keyword makes a variable a constant that cannot be changed?",
                "static", "final", "const", "fixed", "B");
        addQuestion(types, "What is the default type of the literal 3.14?",
                "float", "double", "int", "String", "B");

        // ---------------- Module 2 ----------------
        CourseModule oop = new CourseModule("Object-Oriented Programming",
                "Learn how Java organises code with classes, objects and inheritance.");

        Lesson classes = addLesson(oop, "Classes and Objects", 1, """
                A class is a blueprint that describes the fields (data) and methods (behaviour) of a thing. \
                An object is an instance of a class created with the new keyword.

                public class Student {
                    String name;
                    int marks;

                    // constructor: same name as the class, no return type
                    Student(String name, int marks) {
                        this.name = name;
                        this.marks = marks;
                    }

                    void show() {
                        System.out.println(name + " scored " + marks);
                    }
                }

                Creating and using an object:

                Student s = new Student("Ravi", 85);
                s.show();

                The keyword this refers to the current object.""");
        addQuestion(classes, "Which keyword is used to create an object?",
                "new", "create", "object", "make", "A");
        addQuestion(classes, "A constructor must have...",
                "a return type of void", "the same name as the class", "the static keyword", "no body", "B");
        addQuestion(classes, "A class is best described as...",
                "a running program", "a blueprint for objects", "a variable", "a database table", "B");

        Lesson inheritance = addLesson(oop, "Inheritance and Polymorphism", 2, """
                Inheritance lets a class (the subclass) reuse the fields and methods of another class \
                (the superclass) using the extends keyword.

                class Animal {
                    void sound() { System.out.println("Some sound"); }
                }

                class Dog extends Animal {
                    @Override
                    void sound() { System.out.println("Woof"); }
                }

                Redefining a superclass method in a subclass is called method overriding. \
                Polymorphism means that a superclass reference can hold a subclass object, \
                and the overridden method of the real object runs:

                Animal a = new Dog();
                a.sound();   // prints Woof

                In Java a class can extend only one class (no multiple inheritance of classes), \
                but it can implement many interfaces. The keyword super refers to the superclass.""");
        addQuestion(inheritance, "Which keyword is used to inherit from a class?",
                "implements", "inherits", "extends", "super", "C");
        addQuestion(inheritance, "Redefining a superclass method in a subclass is called...",
                "overloading", "overriding", "hiding", "encapsulation", "B");
        addQuestion(inheritance, "How many classes can a Java class extend directly?",
                "Only one", "Two", "Any number", "None", "A");

        // cascade = ALL saves the lessons and questions together with the module
        moduleRepository.save(basics);
        moduleRepository.save(oop);
    }

    private Lesson addLesson(CourseModule module, String title, int order, String content) {
        Lesson lesson = new Lesson(module, title, content, order);
        module.getLessons().add(lesson);
        return lesson;
    }

    private void addQuestion(Lesson lesson, String text, String a, String b, String c, String d, String correct) {
        lesson.getQuestions().add(new Question(lesson, text, a, b, c, d, correct));
    }
}
