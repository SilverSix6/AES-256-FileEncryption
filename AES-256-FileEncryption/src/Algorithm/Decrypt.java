package Algorithm;

public class Decrypt {
    public static void decrypt(byte[] State, String CipherKey) {
        Inv_KeyExpansion(CipherKey, Inv_ExpandedKey);
        AddRoundKey(State, Inv_ExpandedKey + Nb * Br);

        for(int i = Nr - 1; i > 0; i --)
            Iteration(State, Inv_ExpandedKey + Nb * i);

        FinalIteration(State, Inv_ExpandedKey);
    }

    private static void Inv_KeyExpansion(byte[] CipherKey, byte[] Inv_ExpandedKey){
        KeyExpantion(CipherKey, Inv_ExpandedKey);

        for(int i = 1; i < Nr; i++)
            InvMixColumn(Inv_ExpandedKey + Nb * i);
    }


}
