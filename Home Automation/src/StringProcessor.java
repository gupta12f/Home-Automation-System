/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sarthak
 */
public class StringProcessor {
    
    private final String[] queries = new String[20];
    private final String[] instruction = new String[20];
    private final String[] activation = new String[20];
    private int maxQueryCapacity;
    public enum stringType{
        query, instruction, activation
    }
    
    public StringProcessor(){
        
        setMaxQueryCapacity();
        populateQueries();
        populateInstructions();
        populateActivations();
    }
    private void setMaxQueryCapacity(){
        maxQueryCapacity = 100;
    }
    
    private void populateQueries(){
        queries[0] = "Did I leave any lights turned on?";       
    }
    
    private void populateActivations(){
        activation[0] = "Hi Home";
        activation[1] = "Hi";
        activation[2] = "Hey Home";
        activation[3] = "Hey";
    }
    private void populateInstructions(){
        instruction[0] = "Please turn on the geyser";
        instruction[1] = "Please turn on the AC";
        instruction[2] = "Please turn on the fan";
        instruction[3] = "Please turn off the geyser";
        instruction[4] = "Please turn off the AC";
        instruction[5] = "Please turn off the fan";
         
        instruction[6] = "Please turn off all the appliances of the house";
        instruction[7] = "Please turn on the light 1";
        instruction[8] = "Please turn on the light 2";
        
    }

    
    
    
    public int processString(String message){
        stringType type = checkType(message);
        System.out.println(type);
        int code = -1;
        switch(type){
            case query:
                code = processQuery(message);
                break;
            case activation:
                code = processActivation(message);
                break;
            case instruction:
                code = processInstruction(message);       
                break;                
        }
        return code;
    }
    private stringType checkType(String message){
        if(message.contains("?")){
            return stringType.query;
        }
        else if(message.length()<=10){
            return stringType.activation;
            
        }
        else{
            return stringType.instruction;
        }
        
    }
    
    private int processQuery(String message){
        Boolean found = false;
        int i;
        for(i=0; i<queries.length; i++){
            if(queries[i].equals(message)){
                found = true;
                break;
            }
        }
        if(found){
            return i;
        }
        else{
            return -1;
        }
    }
    
    private int processInstruction(String message){
        Boolean found = false;
        int i;
        for(i=0; i<instruction.length; i++){
            if(instruction[i].equals(message)){
                found = true;
                break;
            }
        }
        if(found){
            return i + maxQueryCapacity;
        }
        else{
            return -1;
        }
    }
    
    private int processActivation(String message){
        Boolean found = false;
        int i;
        for(i=0; i<activation.length; i++){
            if(activation[i].equals(message)){
                found = true;
                break;
            }
        }
        if(found){
            return i + 2*maxQueryCapacity;
        }
        else{
            return -1;
        }
    }
    
}
