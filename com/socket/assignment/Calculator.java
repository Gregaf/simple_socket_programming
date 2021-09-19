package com.socket.assignment;
public class Calculator implements ICalculator
{
    
    public Calculator() 
    {
        super();
    }

    @Override
    public float Add(float x, float y) {
        return (x + y);
    }

    @Override
    public float Subtract(float x, float y) {
        return (x - y);
    }

    @Override
    public float Multiply(float x, float y) {
        return (x * y);
    }

    @Override
    public float Divide(float x, float y) {
        
        if(y == 0) throw new ArithmeticException("Cannot divide by zero!");
            
        return (x / y);
    }
    
}
