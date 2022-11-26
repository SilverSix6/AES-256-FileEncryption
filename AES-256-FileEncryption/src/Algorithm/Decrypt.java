package Algorithm;

public class Decrypt {
    public static final int Nr = 4;
    public static final int Nb = 4;

    public static void decrypt(byte[] State, String CipherKey) {
        Inv_KeyExpansion(CipherKey, Inv_ExpandedKey);
        AddRoundKey(State, Inv_ExpandedKey + Nb * Br);

        for(int i = Nr - 1; i > 0; i --)
            invIteration(State, Inv_ExpandedKey + Nb * i);

        FinalIteration(State, Inv_ExpandedKey);
    }

    private static void Inv_KeyExpansion(byte[] CipherKey, byte[] Inv_ExpandedKey){
        KeyExpantion(CipherKey, Inv_ExpandedKey);

        for(int i = 1; i < Nr; i++)
            InvMixColumn(Inv_ExpandedKey + Nb * i);
    }

    //This is the same as the normal iteration but in reverse order using inverse of each method
    private static void invIteration(byte[]state, byte[] roundKey){
        Encrypt.AddRoundKey(state, roundKey);
        invMixColumn(state);
        invShiftColumn(state);
        invByteSub(state);
    }

    private static void invMixColumn(byte[] state) {

    }

    private static void invShiftColumn(byte[] state) {

    }

    private static void invByteSub(byte[] state) {
    }


}
