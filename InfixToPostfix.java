package cs2321;

/*
 * Alec Trent
 * Assignment 2
 * This class contains the method convert to go from Infix to Post fix expressions.
 */

public class InfixToPostfix {
	/* Convert an infix expression and to a postfix expression
	 * infix expression : operator is between operands. Ex. 3 + 5
	 * postfix Expression: operator is after the operands. Ex. 3 5 +
	 * 
	 * The infixExp expression includes the following
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      and parenthesis: ( , )
	 *      
	 *      For easy parsing the expression, there is a space between operands and operators, parenthesis. 
	 *  	Ex: "1 * ( 3 + 5 )"
	 *      Notice there is no space before the first operand and after the last operand/parentheses. 
	 *  
	 * The postExp includes the following 
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      
	 *      For easy parsing the expression, there should have a space between operands and operators.
	 *      Ex: "1 3 5 + *"
	 *      Notice there is space before the first operand and last operator. 
	 *      Notice that postExp does not have parenthesis. 
	 */

	// Method that contains the switch statements
	static int Operator(char character) 
	{ 
		switch (character) 
		{ 
		case '+': 
		case '-': 
			return 1; 

		case '*': 
		case '/': 
			return 2; 

		case '^': 
			return 3; 
		} 
		return -1; 
	} 

	public static String convert(String infixExp) {
		String answer = new String("");	// an empty list
		DLLStack<Character> stack = new DLLStack<>( ); // an empty stack

		String[] infix = infixExp.split(" ");
		// For loop progresses throughout the length of the infix expression returning the char value 
		// Time Complexity: O(n^2)
		for (int i = 0; i < infix.length; i++) {
			char chars = infix[i].charAt(0); 
			
			// Time Complexity: O(1)
			if (Character.isDigit(chars))
				answer += chars;

			// Time Complexity: O(1)
			else if( chars == '(') 
				stack.push(chars);

			// Time Complexity: O(n)
			else if( chars == ')') {
				while (stack.top() != '(') {
					answer += (" ");
					answer += stack.pop();
				}
				stack.pop();
			}
			// while loop for handling operators
			// Time Complexity: O(n)
			else {
				answer += (" ");
				stack.push(chars);
			}
		}
		// while loop for popping the operators from the stack
		// Time Complexity: O(n)
		while ( stack.isEmpty() != true ) {
			answer += (" ");
			answer += stack.pop();
		}
		return answer;
	}
}