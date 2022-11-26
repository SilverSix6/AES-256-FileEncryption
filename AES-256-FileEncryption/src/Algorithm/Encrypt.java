package Algorithm;

public class Encrypt {
    public final int Nr = 4;
    public final int Nb = 4;


    public static void encrypt(byte[] State, String CipherKey){
        KeyExpansion(CipherKey,ExpandedKey);
        AddRoundKey(State, ExpandedKey);

        for(int i = 1;i < Nr;i++)
            Iteration(State, ExpandedKey + Nb * i);

        FinalIteration(State, ExpandedKey + Nb * Nr);
    }
}

