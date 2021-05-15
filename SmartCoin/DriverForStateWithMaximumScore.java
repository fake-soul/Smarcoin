package SmartCoin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DriverForStateWithMaximumScore {
    public static List<Course> courses = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();
    public static List<StudentDemographic> demographics = new ArrayList<>();
    public static List<StudentCourse> registrations = new ArrayList<>();
    public static void main(String[] args) {
        DriverForStateWithMaximumScore driverForStateWithMaximumScore = new DriverForStateWithMaximumScore();
        int count = StateScore("abc");

        String state = getStateWithMaximumScore(courses, students, demographics, registrations);
        System.out.println(state);

    }

    private static String getStateWithMaximumScore(List<Course> courses, List<Student> students, List<StudentDemographic> demographics, List<StudentCourse> registrations) {
        List<StudentDemographic> listUnique = demographics.stream()
                .filter( distinctByKey(p -> p.state) )
                .collect( Collectors.toList() );
        int max = 0;
        String stateName = "";
        for(StudentDemographic studentDemographic : listUnique) {
            int count = StateScore(studentDemographic.state);
            if(count>max) {
                max = count;
                stateName = studentDemographic.state;
            }
        }
        return stateName;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static int StateScore(String stateName) {
        HashSet<Long> courseSet = new HashSet<>();
        for(StudentDemographic studentDemographic : demographics) {
            if(stateName.equalsIgnoreCase(studentDemographic.state)) {
                long demoGraphicId = studentDemographic.id;
                List<Student> studentList = students.stream().filter(student1 -> student1.id == demoGraphicId).collect(Collectors.toList());
                if(!studentList.isEmpty()) {
                    Student student = studentList.get(0);
                    List<StudentCourse> courses = registrations.stream().filter(registrations -> registrations.studentId == student.id).collect(Collectors.toList());
                    for(StudentCourse studentCourse : courses) {
                        courseSet.add(studentCourse.courseId);
                    }
                }
            }
        }
        return courseSet.size();
    }
}
