package Algorithm;

public class Decrypt {
    public static final int[][] INV_S_BOX = {
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
    };

    public static final int Nr = 4;
    public static final int Nb = 4;

    
    public static void decrypt(byte[] State, String CipherKey) {
        Inv_KeyExpansion(CipherKey, Inv_ExpandedKey);
        AddRoundKey(State, Inv_ExpandedKey + Nb * Br);

        for(int i = Nr - 1; i > 0; i --)
            invIteration(State, Inv_ExpandedKey + Nb * i);

        invFinalIteration(State, Inv_ExpandedKey);
    }



    //This is the same as the normal iteration but in reverse order using inverse of each method
    private static void invIteration(int[]state, int[] roundKey){
        Encrypt.AddIterationKey(state, roundKey);
        invMixColumn(state);
        invShiftRow(state);
        invByteSub(state);
    }

    private static void invFinalIteration(int[]state, int[]roundKey){
        Encrypt.AddIterationKey(state, roundKey);
        invShiftRow(state);
        invByteSub(state);
    }


    static void invMixColumn(int[] state) {
    /*Matrix product between,
            [14 11 13  9] [C0] = 14*C0 + 11*C1 + 13*C2 +  9*C3 = C0
            [ 9 15 11 13] [C1] =  9*C0 + 14*C1 + 11*C2 + 13*C3 = C1
            [13  9 14 11] [C2] = 13*C0 +  9*C1 + 14*C2 + 11*C3 = C2
            [11 13  9 14] [C3] = 11*C0 + 13*C1 +  9*C2 + 14*C3 = C3

            then take modulo 8 to keep in GF(2^4)
         */
        for(int i = 0; i < 4; i++) {
            int[] stateData = new int[4];
            stateData[0] = (state[0] >> (i * 8)) & 0xFF;
            stateData[1] = (state[1] >> (i * 8)) & 0xFF;
            stateData[2] = (state[2] >> (i * 8)) & 0xFF;
            stateData[3] = (state[3] >> (i * 8)) & 0xFF;


            //Addition operations in galois field are computed using bitwise xor function
            //Constant multiples need to be multiplied using a galois_multiplication function

            state[0] = state[0] - (stateData[0] << (i*8)) + ((Encrypt.Galois_Multiplication(stateData[3], 9) ^ Encrypt.Galois_Multiplication(stateData[1], 11) ^ Encrypt.Galois_Multiplication(stateData[2], 13) ^ Encrypt.Galois_Multiplication(stateData[0], 14)) << (i * 8));
            state[1] = state[1] - (stateData[1] << (i*8)) + ((Encrypt.Galois_Multiplication(stateData[0], 9) ^ Encrypt.Galois_Multiplication(stateData[2], 11) ^ Encrypt.Galois_Multiplication(stateData[3], 13) ^ Encrypt.Galois_Multiplication(stateData[1], 14)) << (i * 8));
            state[2] = state[2] - (stateData[2] << (i*8)) + ((Encrypt.Galois_Multiplication(stateData[1], 9) ^ Encrypt.Galois_Multiplication(stateData[3], 11) ^ Encrypt.Galois_Multiplication(stateData[0], 13) ^ Encrypt.Galois_Multiplication(stateData[2], 14)) << (i * 8));
            state[3] = state[3] - (stateData[3] << (i*8)) + ((Encrypt.Galois_Multiplication(stateData[2], 9) ^ Encrypt.Galois_Multiplication(stateData[0], 11) ^ Encrypt.Galois_Multiplication(stateData[1], 13) ^ Encrypt.Galois_Multiplication(stateData[3], 14)) << (i * 8));

        }
    }



    static void invShiftRow(int[] state) {
        //Split state into 4 8 bit values
        int[] ints = new int[4];
        ints[0] = (state[1] >> 24) & 0xFF;
        ints[1] = (state[1] >> 16) & 0xFF;
        ints[2] = (state[1] >> 8) & 0xFF;
        ints[3] = state[1] & 0xFF;

        state[1] = (ints[3] << 24) + (ints[0] << 16) + (ints[1] << 8) + (ints[2]);
        //row 2
        int[] ints2 = new int[4];
        ints2[0] = (state[2] >> 24) & 0xFF;
        ints2[1] = (state[2] >> 16) & 0xFF;
        ints2[2] = (state[2] >> 8) & 0xFF;
        ints2[3] = state[2] & 0xFF;

        state[2] = (ints2[2] << 24) + (ints2[3] << 16) + (ints2[0] << 8) + (ints2[1]);

        //row 3
        int[] ints3 = new int[4];
        ints3[0] = (state[3] >> 24) & 0xFF;
        ints3[1] = (state[3] >> 16) & 0xFF;
        ints3[2] = (state[3] >> 8) & 0xFF;
        ints3[3] = state[3] & 0xFF;

        state[3] = (ints3[1] << 24) + (ints3[2] << 16) + (ints3[3] << 8) + (ints3[0]);
    }

    static void invByteSub(int[] state) {
        for(int c = 0; c < 4; c++){
            int[] ints = new int[4];
            ints[0] = (state[c] >> 24) & 0xFF;
            ints[1] = (state[c] >> 16) & 0xFF;
            ints[2] = (state[c] >> 8) & 0xFF;
            ints[3] = state[c] & 0xFF;

            for(int i = 0; i < 4; i++){
                ints[i] = INV_S_BOX[(ints[i] >> 4) & 0xF][ ints[i] & 0xF ];
            }
            state[c] = (ints[0] << 24) + (ints[1] << 16) + (ints[2] << 8) + (ints[3]);
        }
    }




}
