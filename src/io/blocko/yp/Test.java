/**
 *  Blocko Inc.
 * __________________
 * 
 *  (C) 2017 Blocko Inc. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Blocko Inc. and its suppliers, if any.
 * The intellectual and technical concepts contained herein are
 * proprietary to Blocko Inc. and its suppliers
 * and may be covered by South Korea and Foreign Patents, 
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Blocko Inc.
 */
package io.blocko.yp;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Yun Park <hanlsin@blocko.io>
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random(System.currentTimeMillis());
		for (int i = 0; i < 10; i++) {
			System.out.println("Randome number = " + rand.nextInt(10));
		}

		LinkedList<String> ll = new LinkedList<>();
		ll.add("1");
		ll.add("2");
		ll.add("3");
		ll.add("4");
		ll.add("5");
		System.out.println("LL size = " + ll.size());

		for (int i = 0; i < 10; i++) {
			String str = ll.pollLast();
			System.out.println(str);
			ll.push(str);
		}
		
		System.out.println();

		for (int i = 0; i < 10; i++) {
			String str = ll.poll();
			System.out.println(str);
			ll.add(str);
		}
		
		System.out.println();

		for (int i = 0; i < 10; i++) {
			String str = ll.poll();
			System.out.println(str);
			ll.push(str);
		}
	}

}
