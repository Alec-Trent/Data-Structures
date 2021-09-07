package cs2321;

/*
 * Alec Trent
 * Assignment 2
 * This class contains the method evaluate to get answers from Post fix expressions.
 */

public class PostfixExpression {

	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * 
	 * @param exp The postfix expression
	 * @return the result of the expression
	 */
	public static int evaluate(String exp) {
		DLLStack<Integer> stack = new DLLStack<>( ); // an empty stack
		String[] array = exp.split(" ");
		System.out.println(array.length);

		// For loop progresses throughout the length of the expression returning the char value
		// Time Complexity: O(n^2)
		for(int i = 0; i < array.length; i++) { 
			String s = array[i];
			
			// Progresses through white space
			// Time Complexity: O(1)
			if (array[i] == " ") {
				continue;
			}

			// If its an number, push it onto the stack
			// Time Complexity: O(1)
		
			else if(Character.isDigit(s.charAt(0))){
				stack.push(Integer.valueOf(s)); // Convert character to int using ascii table value
			}

			
			// If its a sign, pop two elements from stack and perform the operation
			// Time Complexity: O(1)
			else { 
				int number1 = stack.pop(); 
				int number2  = stack.pop(); 

				// Switch statement to cycle through each possible sign
				// Time Complexity: O(n)
				switch(s.charAt(0)) { 
				case '+': 
					stack.push(number2 + number1); 
					break; 

				case '-': 
					stack.push(number2 - number1); 
					break; 

				case '/': 
					stack.push(number2 / number1); 
					break; 

				case '*': 
					stack.push(number2 * number1); 
					break; 
				} 
			} 
		} 
		return stack.pop();  
	}
}