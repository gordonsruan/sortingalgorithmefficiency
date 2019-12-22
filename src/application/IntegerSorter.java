package application;

//Putting Comment shell under application, in case of comment shell not showing because of being in first lines of code
//Name: Gordon Ruan
//Class: ICS 4U1
//Instructor: Mr. Radulovic
//Due Date: November 10,2019
//Assignment Name: Sorting Algorithms and Efficiency Assignment
//This program takes an array of elements of type integer, and sorts it from lowest value to highest value.
//The different sorting methods examples of different ways to achieve the final result of an ordered array.

//Imports to read file
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IntegerSorter implements Sorter {

	private int[] list = new int[0]; // Global list, this is the list that user will set and be referred to

	public void getFile(String file) { // Method that reads file and gets all elements, and sets it as the
										// list to order ARGUMENT IS FILE PATH
		ArrayList<Integer> fileArray = new ArrayList<Integer>();
		try {
			File file1 = new File(file);
			Scanner scanner = new Scanner(file1);
			while (scanner.hasNextInt()) { // checks every integer in the file
				fileArray.add(scanner.nextInt()); // adds it to arraylist
			}
			scanner.close();
		} catch (FileNotFoundException e) { // error if file path is incorrect
			System.out.println("File not found");
		}
		int[] tempList = new int[fileArray.size()]; // Makes an array the same size as the arraylist
													// with file points
		for (int i = 0; i < fileArray.size(); i++) { // copies arraylist to array
			tempList[i] = (int) fileArray.get(i);
		}
		this.setList(tempList); // sets array as the global list
	}

	public void sortMethod1() { // First sorting method
		for (int i = list.length - 1; i > 0; i--) { // goes from last index to first
			for (int z = 0; z < i; z++) { // goes through every index while it is
											// less than the one that will be compared to
				if (list[z] > list[z + 1]) { // sees if z is greater than the next element in list
					int temp = list[z + 1]; // swaps z and next element if it is greater
					this.list[z + 1] = list[z];
					list[z] = temp;
				}
			} // Ultimately, each iteration sets the highest element of
				// the list at the end and goes through putting next highest element
				// one under the previous
		}
	}

	public void sortMethod2() { // Second sorting method
		for (int i = 0; i < list.length; i++) { // goes from first to last index
			for (int z = i + 1; z < list.length; z++) { // goes through every element in array other than
														// sorted ones
				if (list[z] < list[i]) { // if an element is less than the one that is compared to everything
					int temp = list[z]; // swaps the two elements
					list[z] = list[i];
					list[i] = temp;
				}
			}
		} // Each iteration sets the lowest element to the top of the list and next lowest
			// to the next element
	}

	public void combineArray(int low, int high) { // Helper method to sorting method 3
		int[] newArray = new int[getList().length]; // makes an array with size of the global array
		for (int i = low; i <= high; i++) { // copies elements which are to be changed to the temporary list
			newArray[i] = list[i];
		}
		int mid = (low + high) / 2; // sets index for middle of the list for simplicity
		int aIndex = low; // sets starting index for list of elements nearer to front of global list
		int bIndex = mid + 1; // sets starting index for list of elements nearer to end of global list
		int z = low; // array to be changed in global list
		while ((aIndex <= mid) && (bIndex <= high)) { // while corresponding indices are within both "split arrays"
			list[z] = Math.min(newArray[aIndex], newArray[bIndex]); // adds lower value of two in the "split arrays" to
																	// the global list
			if (newArray[aIndex] < newArray[bIndex]) { // Increases index corresponding to whichever split array the new
														// element came from
				aIndex += 1;
			} else {
				bIndex += 1;
			}
			z += 1; // goes to next item in global list
		}
		while (bIndex <= high) { // If the first split array has been fully gone through
			list[z] = newArray[bIndex]; // adds rest of the second array into the global array
			bIndex += 1;
			z += 1;
		}

		while (aIndex <= mid) { // if the second split array has been fully gone through
			list[z] = newArray[aIndex]; // adds rest of first array into the global array
			aIndex += 1;
			z += 1;
		}

	}

	public void sortMethod3(int low, int high) { // Recursive method that splits array down into 1 element,
		if (low == high) {
			return;
		} else {
			int mid = (low + high) / 2;
			sortMethod3(low, mid); // splits array into two halves
			sortMethod3(mid + 1, high);
			combineArray(low, high); // calls combineArray method on the split arrays when size is 1 and continues on

		}
	}

	@Override
	public void setList(int[] list) { // sets global list to be one in the argument
		// TODO Auto-generated method stub
		this.list = list;
	}

	@Override
	public int[] getList() { // returns global list
		return this.list;
	}

	public String toString() { // adds array's elements to a string, and returns string with all of global
								// array's elements
		String string = "";
		for (int i = 0; i < list.length; i++) {
			string = string + list[i] + " ";
		}
		return string;
	}

	@Override
	public void sort(int type) { // calls sort different sort methods with the argument
		// TODO Auto-generated method stub
		if (type == 1) {
			this.sortMethod1();
		} else if (type == 2) {
			this.sortMethod2();
		} else if (type == 3) {
			this.sortMethod3(0, list.length - 1);
		} else {
			System.out.print("Invalid sorting type");
		}

	}

	public static void main(String[] args) {
		IntegerSorter sort = new IntegerSorter();

		sort.getFile( // Put path to file here
				(String) "C:/Users/GordonSiRuan/eclipse-workspace/SortingEfficiencyAssignment/src/application/2power19.txt");

		double time1 = System.nanoTime();
		sort.sort(1);
		double time2 = System.nanoTime();
		System.out.println((time2 - time1)); // Time in seconds system took to sort

	}
}
