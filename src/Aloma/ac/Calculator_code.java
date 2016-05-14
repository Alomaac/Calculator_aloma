package Aloma.ac;

public class Calculator_code {

	private int first;
	private int second;
	/**
	 * @param user_input 
	 * This function takes input from user as string 
	 * @return  result of all calculation as string 
	 */
	public String Solve(String user_input)
	{
		String rt=solve_between_brackets('('+user_input+')');
		
		if(rt.endsWith(".0") == true )
			rt=rt.substring(0, rt.length()-2);
		return rt; 
	}
	/////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param d1  first parameter that we want to calculate it's multiplicative
	 * @param d2  second parameter the power of first parameter
	 * @return result as double value
	 */
	private double squa(double d1,double d2)
	{
		String d4=String.valueOf(d1);
		int i=d4.length();
		for(int j=d4.length()-1;j>0;j--)
		{
			if(d4.charAt(j)=='('||d4.charAt(j)=='+'||d4.charAt(j)=='-'||d4.charAt(j)=='/'||d4.charAt(j)=='*'||d4.charAt(j)=='%')
			{
				d4=d4.substring(j,i);
			}
			
		}
		Double d5=Double.valueOf(d4);
		  final double D3=d1;
		for(int c=1;c<d2;c++)
		{
			d5*=D3;
		}
		
		return d5;
		
	}
	////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * @param user_input a subexpression comes from user between brackets  
	 * this function calculates expression between brackets in the main expression
	 * @return result from that subexpression as string value
	 */
	private String solve_between_brackets(String user_input)
	{
		int f=0;
		for(int i=0; i<user_input.length(); ++i)
		{
			if(user_input.charAt(i)=='(')
			{
				f=i;
			}
			else if(user_input.charAt(i)==')')
			{
				String ans=det_sign(user_input.substring(f+1, i));
				String s1=user_input.substring(0,f);
				String s2=user_input.substring(i+1);
				return solve_between_brackets(s1+ans+s2);
			}
		}
		return user_input;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param input  subexpression comes from larger expression from brackets after any operators 
	 * @param i index of operator sign 
	 * this function calculates  subexpression comes from larger expression from brackets after any operators 
	 * @return number after operator and defiened stop when meet another operation in the expression
	 */
	private double cul_after(String input, int i) {
		
		String str="";
		for(int j=i+1; j<input.length(); ++j)
			if(Character.isDigit(input.charAt(j)) == true  || input.charAt(j)=='.')
			{
				str += input.charAt(j);
				second = j ;
			}
			else break;
		return Double.valueOf(str);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param input subexpression from larger expression from brackets befor any operators
	 * @param i index of operator sign
	 * this function calculates  subexpression comes from larger expression from brackets befor any operators  
	 * @return number befor operator and defiened stop when meet another operation in the expression
	 */
	private double cul_before(String input, int i) {
		
		String str="";
		for(int j=i-1; j>=0; --j)
			if(Character.isDigit(input.charAt(j)) == true  || input.charAt(j)=='.')
			{
				str = input.charAt(j) + str;
				first = j;
			}
			else break;
		
		if(str=="")str="0";
		return Double.valueOf(str);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param input 
	 * this function calculates the expression that containes (* ,/) 
	 * and tests if my expression has (-) operator before it ,to make the value negative  value 
	 * @return the value of the (devision ,multiplication ) expression 
	 */
	private String mul_div(String input)
	{
		for(int i=0; i<input.length(); ++i)
		{
			if(input.charAt(i)=='*' )
			{			
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				input = input.substring(0,first) +  Double.valueOf(d1*d2).toString() + input.substring(second +1)  ;
				i=-1; //re_cul
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(input.charAt(i)=='/')
			{
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				input = input.substring(0,first) +  Double.valueOf(d1/d2).toString() + input.substring(second +1)  ;
				i=-1; //re_cul
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(input.charAt(i)=='^')
			{
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				input = input.substring(0,first) +  squa(d1,d2) + input.substring(second +1)  ;
				i=-1; //re_cul
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
			else 
			if(input.charAt(i)=='%')
			{
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				
				input = input.substring(0,first) +  Double.valueOf(d1%d2).toString() + input.substring(second +1)  ;
				i=-1; //re_cul
			}
					
		}
		return input;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param input 
	 * this function calculates the expression that containes (+ ,-) 
	 * and tests if my expression has (-) operator before it ,to make the value negative  value 
	 * @return the value of the (summtion,subtraction ) expression 
	 */
	private String add_sub(String input)
	{
		for(int i=0; i<input.length(); ++i)
		{
			if(input.charAt(i)=='+' )
			{double d1=cul_before(input,i);
			double d2;
			if(first>0 && input.charAt(first-1)=='-')
			{
				d1*=-1;
				first--;
			}
			if(input.charAt(i+1)=='-')
			{
				d2=-1*cul_after(input,i+1);
			}
			else
			{
				d2=cul_after(input,i);
			}
				input = input.substring(0,first) + Double.valueOf(d1+d2).toString() + input.substring(second +1);
				i=-1;
				
			}
			//////////////////////////////////////////////////////////////////////////////////
			else if(input.charAt(i)=='^')
			{
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				input = input.substring(0,first) +  squa(d1,d2) + input.substring(second +1)  ;
				i=-1; //re_cul
			}
			/////////////////////////////////////////////////////////////////////////////////////////
			else if(input.charAt(i)=='-')
			{
				double d1=cul_before(input,i);
				double d2;
				if(first>0 && input.charAt(first-1)=='-')
				{
					d1*=-1;
					first--;
				}
				if(input.charAt(i+1)=='-')
				{
					d2=-1*cul_after(input,i+1);
				}
				else
				{
					d2=cul_after(input,i);
				}
				input = input.substring(0,first) + Double.valueOf(d1 - d2).toString() + input.substring(second +1);
				if(d1==0)continue;
				i=-1;
			}		
		}
		return input;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param input expression or subexpression 
	 * this function to find which subexpression comes first depending on math periorty   
	 * @return the result of the all main expressen that user enter it 
	 */
	private String det_sign(String input)
	{
		input = mul_div(input);
		input = add_sub(input);
		return input;
	}
	
	
}