package com.cdl.kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *
 * @author tarika
 *
 */

public class PriceCalculation {

	static Map<String, Double[]> itemsAvailable = new HashMap<String, Double[]>();
	static Double amount = 0.00;
	static List<String> newCount = new ArrayList<>();

	// setting per unit and special offer prices
	public static void setPrice() {
		/*
		 * In map's array value first, second & third elements are per unit price, no.
		 * of unit applicable for special offer & special offer price respectively.
		 */
		itemsAvailable.put("A", new Double[] { 0.50, 3.0, 1.30 });
		itemsAvailable.put("B", new Double[] { 0.30, 2.0, 0.45 });
		itemsAvailable.put("C", new Double[] { 0.20, 0.0, 0.00 });
		itemsAvailable.put("D", new Double[] { 0.15, 0.0, 0.00 });
	}

	// scanning item together
	@SuppressWarnings("resource")
	public static Map<String, Long> scanItems() {

		// Getting scanned items array
		Scanner in = new Scanner(System.in);
		String data[] = in.nextLine().split(" ");
		List<String> itemPurchased = new ArrayList<>();
		Stream<String> stream1 = Arrays.stream(data);
		stream1.forEach(entry -> {
			String entry1 = entry.toUpperCase();
			if (itemsAvailable.containsKey(entry1)) {
				itemPurchased.add(entry1);
			} else {
				System.out.println("Sorry " + entry1 + " is not in stock.");
			}
		});

		// for scanning per item
		newCount.addAll(itemPurchased);

		// grouping repeatedly scanned items
		Map<String, Long> counts = newCount.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return counts;
	}

	// scanning items separately
	public static void scanAgain() {

		// Asking to scan items
		System.out.println("\n" + "Enter item/items to be scanned:");

		// collecting scanned items
		Map<String, Long> counts = scanItems();

		// calculating total amount
		calculateAmount(counts, itemsAvailable);

	}

	@SuppressWarnings("resource")
	public static void checkForMoreScan() {
		// asking the requirement
		System.out.print("Need to scan more: yes/no?");
		Scanner in = new Scanner(System.in);
		String data = in.nextLine();
		int counter = 0;
		if (data.toLowerCase().equals("yes"))
			// limiting frequency to finite value once again
			while (counter < 100) {
				scanAgain();
				counter++;
			}
		else
			System.out.println("\n");
		System.out.println("Thank you for shopping with us!");

	}

	// Calculate price
	public static void calculateAmount(Map<String, Long> counts, Map<String, Double[]> itemsAvailable) {
		counts.forEach((k, v) -> {
			Double valueArray[] = itemsAvailable.get(k);

			// checking special offer
			if (v >= valueArray[1] && valueArray[1] != 0.0) {
				amount = amount + (Math.floor(v / valueArray[1]) * valueArray[2]) + (v % valueArray[1] * valueArray[0]);
			} else {
				amount = amount + (v * valueArray[0]);

			}
		});
		System.out.print("Your total amount is: " + amount);
		System.out.println("\n");

		amount = 0.0;
	}

	public static void main(String[] args) {

		// set special and per unit price
		setPrice();

		// Mention available items
		System.out.print("Items available for purchase are following: ");
		System.out.println("\n");
	    itemsAvailable.keySet().stream().forEach(el -> System.out.println(el));

		// limiting frequency to finite value
		int counter = 0;
		while (counter < 5) {
			scanAgain();
			counter++;
		}
		checkForMoreScan();
	}

}
