import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.*;
import java.util.function.*;
import java.util.Map;

/** The goal of this practice is not to "get the answer" through AI or some other means, but for you to think through the questions and
* come up with a strategy. You can decide not to do it at your own cost.
*/

/**
* In the following, write code to achieve what's asked. You don't need to but if you want to very the accuracy of your code,
* include statements to print the result.
*/

/** Submit to the TA, and he will assign you a grade based on a few selected responses.  */





public class Week10_labs
{
    public static void main(String[] args)
	{
        List<String> fruit = Arrays.asList("cherry","banana","berry","apple","cherry","kiwi","fig","date","lemon","honeydew","cherry","elderberry","apple","banana","grape");

		// Collect elements into a Set

        // Collect the fruit into groups based on their first character

		// Group fruit by the length of the name

		//Collect the fruit that has erry in it

		//Create a partition of fruit based on if it contains erry

		//collect/ the fruit that has 5 or less symbols

		//find the total number of symbols in all the fruit stored

		// This collects all of the fruit into a set. 
        Set<String> fruitSet = fruit.stream().collect(Collectors.toSet());

        // This one collects the fruit into groups based on their first character
        Map<Character, List<String>> byFirstChar = fruit.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));

        // This one measures the length of the fruit and divides them into seperate sets.
        Map<Integer, List<String>> byLength = fruit.stream()
                .collect(Collectors.groupingBy(String::length));

        // If there is a fruit with "erry" at the end then it will be collected here. 
        List<String> containsErry = fruit.stream()
                .filter(s -> s.contains("erry"))
                .collect(Collectors.toList());

        // Create a partition of fruit based on if it contains erry
        Map<Boolean, List<String>> partitionedErry = fruit.stream()
                .collect(Collectors.partitioningBy(s -> s.contains("erry")));

        // This one collects all the fruit with 5 or less symbols. 
        List<String> shortFruit = fruit.stream()
                .filter(s -> s.length() <= 5)
                .collect(Collectors.toList());

        // This finds the total number of symbols in all the fruit stored
        int totalSymbols = fruit.stream()
                .mapToInt(String::length)
                .sum();


		List<Integer> data = Arrays.asList(87, 23, 45, 100, 6, 78, 92, 44, 13, 56, 34, 99, 82, 19, 1012, 78, 45, 90, 23, 56, 78, 100, 3, 43, 67, 89, 21, 34, 10);

        // Partition data based on if >=50

		//divide data into groups based on the remainder when divided by 7


		//find the sum of the data


		//collect the unique values

        //compute the cube of each values

		//find the sum of the cubes of each value

		//increase the value of each element by 5

		//compute the cube of the even values

		// This one creates a partition if the value is greater than or equal to 50. 
        Map<Boolean, List<Integer>> gte50 = data.stream()
                .collect(Collectors.partitioningBy(n -> n >= 50));

        // This one divides the data into groups based on the remainder when divided by 7.
        Map<Integer, List<Integer>> byRemainder7 = data.stream()
                .collect(Collectors.groupingBy(n -> n % 7));

        // This one finds the sum of all of the data.
        int sumData = data.stream().mapToInt(Integer::intValue).sum();

        // This one simply collects the unique values
        List<Integer> uniqueData = data.stream().distinct().collect(Collectors.toList());

        // This one just cubes the value. 
        List<Integer> cubes = data.stream().map(n -> n * n * n).collect(Collectors.toList());

        // This one finds the sum of the cubed version of each value. 
        long sumCubes = data.stream().mapToLong(n -> (long) n * n * n).sum();

        // This one increases the value of each element by 5.
        List<Integer> plusFive = data.stream().map(n -> n + 5).collect(Collectors.toList());

        // If the value is even, then the value is multipled by itself 3 times. 
        List<Integer> evenCubes = data.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n * n)
                .collect(Collectors.toList());

   }
}
