package SIC;
import java.io.IOException;
public class SymTab {
    static String[] allAddressArray;       //to store the address locations of everyline b\w START & END by splitting the string builder
    static String[] symAddressArray;       //to store the Symbol & address locations of every symbol by splitting the string builder
    static String programName;          //to store the program name
    static int startingAddress = 0;     //just to store the starting address
    static int thisAddress;             // to total the address calculation    
    public static void Start(String symtabFileLocation) throws IOException{
        StringBuilder allAddressSB = new StringBuilder();
        StringBuilder symSB = new StringBuilder();
        boolean programStart = false;
        for(int i = 0;;i++){
            if(Input.Program[i].equals("END"))
                break;
            if(programStart){
                if(Input.Program[i].equals("RESW")){
                    allAddressSB.append(Integer.toHexString(thisAddress)).append(" ");
                    symSB.append(Input.Program[i-1]).append(" ").append(Integer.toHexString(thisAddress)).append(" ").append(System.getProperty("line.separator"));
                    thisAddress = thisAddress + (Integer.parseInt(Input.Program[i+1]) * 3);
                    //System.out.println(Integer.parseInt(Input.Program[i+1]) * 3);
                   // System.out.println(Integer.toHexString(thisAddress));
                }
                if(Input.Program[i].equals("RESB")){
                    allAddressSB.append(Integer.toHexString(thisAddress)).append(" ");
                    symSB.append(Input.Program[i-1]).append(" ").append(Integer.toHexString(thisAddress)).append(" ").append(System.getProperty("line.separator"));
                    thisAddress = thisAddress + Integer.parseInt(Input.Program[i+1]);
                     //thisAddress =Integer.parseInt(Integer.toHexString(thisAddress));
                    //System.out.println(thisAddress);
                }
                if(Input.Program[i].equals("WORD")){
                    allAddressSB.append(Integer.toHexString(thisAddress)).append(" ");
                    symSB.append(Input.Program[i-1]).append(" ").append(Integer.toHexString(thisAddress)).append(" ").append(System.getProperty("line.separator"));
                    thisAddress = thisAddress + 3;
                    //thisAddress =Integer.parseInt(Integer.toHexString(thisAddress));
                   // System.out.println(Integer.toHexString(thisAddress));
                }
                if(Input.Program[i].equals("BYTE")){
                    allAddressSB.append(Integer.toHexString(thisAddress)).append(" ");
                    symSB.append(Input.Program[i-1]).append(" ").append(Integer.toHexString(thisAddress)).append(" ").append(System.getProperty("line.separator"));
                    thisAddress = thisAddress + 1;
                    //thisAddress =Integer.parseInt(Integer.toHexString(thisAddress));
                   // System.out.println(thisAddress);
                }
                if(OpTab.OpcodeMatcher(Input.Program[i])){
                   // System.out.println((thisAddress));
                    allAddressSB.append(Integer.toHexString(thisAddress)).append(" ");
                    thisAddress = thisAddress + 3;
                    // thisAddress =Integer.parseInt(Integer.toHexString(thisAddress));
                    //System.out.println((thisAddress));
                }
            }
            if("START".equals(Input.Program[i])){
                programName = Input.Program[i-1];                       //got the program name
                startingAddress = Integer.parseInt(Input.Program[i+1],16); //got the starting address
                thisAddress = startingAddress;                   //starting address is taken as the current address
                programStart = true;                                               //set the boolean to start address calculating if statement written above
            }
        }
        System.out.println((allAddressSB));
        allAddressArray = Ex6Methods.ToStringAndSplit(allAddressSB, " ");
        symAddressArray = Ex6Methods.ToStringAndSplit(symSB, " ");
        OpTab.OpcodeInOrderArray = Ex6Methods.ToStringAndSplit(OpTab.OpcodeInOrderSB, " ");
        Ex6Methods.WriteFile(symtabFileLocation, symSB.toString());
    }
    public static boolean SymMatcher(String Opcode){
        for (String symAddressArray1 : symAddressArray) {
            if (symAddressArray1.equals(Opcode))
                return true;
            if (symAddressArray1.equals(System.getProperty("line.separator") + Opcode))
                return true;
        }
        return false;
    }
    public static boolean DataTypeChecker(String DataType){
        return DataType.equals("RESW") || DataType.equals("RESB") || DataType.equals("WORD") || DataType.equals("BYTE");
    }
}