package com.hust.kstn.parameters;

public class TestingParameters {
    public static void main(String[] args) {
        Student s1 = new Student(1, "A");
        Student s2 = new Student(2, "B");

        swap(s1, s2);

        System.out.println("First student: " + s1.getName());
        System.out.println("Second student: " + s2.getName());
    }

    public static void swap(Object o1, Object o2) {
        int tempStudentId = ((Student)o1).getStudentId();
        String tempName = ((Student)o1).getName();

        ((Student)o1).setStudentId(((Student)o2).getStudentId());
        ((Student)o1).setName(((Student)o2).getName());

        ((Student)o2).setStudentId(tempStudentId);
        ((Student)o2).setName(tempName);
    }
}

