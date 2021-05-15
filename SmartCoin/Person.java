package SmartCoin;

import java.util.*;
import java.util.stream.Collectors;

public class Person implements Comparable<Person> {
    public int id;
    public String name;
    public Date dob;
    public String state;

    public Person(int id, String name, Date dob){
        this.id = id;
        this.name = name;
        this.dob = dob;
    }
    public Person(int id, String name, Date dob, String state){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.state = state;
    }

    @Override
    public int compareTo(Person p) {
        return this.dob.compareTo(p.dob);
    }
//    @Override
//    public int compareTo(Date d) {
//        return this.dob.compareTo(d);
//    }
    static void sort(List<Person> people){
        Collections.sort(people);
    }

    static List<Person> findInN(List<Person> people, Date dob) {
        List<Person> p = people.stream()
                    .filter(person -> person.dob.equals(dob))
                    .collect(Collectors.toList());
        return p;
    }

    static List<Person> findOptimise(List<Person> people, Date dob) {
        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.dob.compareTo(o2.dob);
            }

//            public int compare(Person person, Date date) {
//                return person.dob.compareTo(date);
//            }
        };


        int index = Collections.binarySearch(people, new Person(-1, "random", dob), comparator);
        List<Person> personList = new ArrayList<>();
        if(index<0 || index>people.size())
            return new ArrayList<>();
        personList.add(people.get(index));
        int leftPointer = index-1;
        int rightPointer = index+1;
        while (leftPointer > 0) {
            if(!people.get(leftPointer).dob.equals(dob))
                break;
            personList.add(people.get(leftPointer));
            leftPointer--;
        }
        while (rightPointer < people.size()) {
            if(!people.get(rightPointer).dob.equals(dob))
                break;
            personList.add(people.get(rightPointer));
            rightPointer++;
        }
        return personList;
    }

    static String maxState(List<Person> people) {
        HashMap<String, Integer> stateMap = new HashMap<String, Integer>();
        for(Person p : people) {
            if(stateMap.containsKey(p.state)){
                int count = stateMap.get(p.state);
                count++;
                stateMap.put(p.state, count);
            }
            else
                stateMap.put(p.state, 1);
        }
        int max = 0;
        String stateValue = "";
        for(String state: stateMap.keySet()) {
            int count = stateMap.get(state);
            if(count>max) {
                max = count;
                stateValue = state;
            }
        }
        return stateValue;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Person> personList = new ArrayList<>();
        Person p1 = new Person(1, "one", new Date(System.currentTimeMillis()));
        Person p2 = new Person(2, "two", new Date(System.currentTimeMillis()));
        Thread.sleep(1000);
        Person p3 = new Person(3, "three", new Date(System.currentTimeMillis()));


        personList.add(p1); personList.add(p2); personList.add(p3);
        printTheList(personList);
        sort(personList); // Sorted List
        printTheList(personList);

        List<Person> inNtime = findInN(personList, new Date(System.currentTimeMillis()));
        System.out.println("Ntime Search ");
        printTheList(inNtime);
        List<Person> inOptimizeTime = findOptimise(personList, new Date(System.currentTimeMillis()));
        System.out.println("optimize search ");
        printTheList(inOptimizeTime);


        p1 = new Person(1, "one", new Date(System.currentTimeMillis()), "s1");
        p2 = new Person(2, "two", new Date(System.currentTimeMillis()), "s3");
        p3 = new Person(3, "three", new Date(System.currentTimeMillis()), "s3");
        List<Person> personList2 = new ArrayList<>();
        personList2.add(p1); personList2.add(p2); personList2.add(p3);
        printTheList(personList2);
        System.out.println(maxState(personList2));

    }
    public static void printTheList(List<Person> personList) {
        if(personList == null || personList.isEmpty())
            return;
        System.out.println("----------------------");
        for (Person p : personList) {
            System.out.println("Id "+ p.id+ " Name "+ p.name+ " dob "+ p.dob+ " state "+ p.state);
        }
        System.out.println("----------------------");
    }
}
