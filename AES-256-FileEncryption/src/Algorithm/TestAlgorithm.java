package Algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.apache.commons.codec.digest.DigestUtils.*;

public class TestAlgorithm {

    @BeforeEach
    public void init(){

    }

    @Test
    public void testRowShift(){
        int[] state = new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6};
        Encrypt.ShiftRow(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x8e9f01c6, 0xdc01c64d, 0x01c6a158, 0xc6bc9d01}));
    }

    @Test
    public void testInvRowShift(){
        int[] state = new int[]{0x8e9f01c6, 0xdc01c64d, 0x01c6a158, 0xc6bc9d01};
        Decrypt.invShiftRow(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6}));
    }

    @Test
    public void testInvertabilityRowShift(){
        int[] state = new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd};
        Encrypt.ShiftRow(state);
        Decrypt.invShiftRow(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd}));
    }


    @Test
    public void testByteSub(){
        int[] state = new int[]{0x8e9ff1c6, 0x4ddce1c7, 0xa158d1c8, 0xbc9dc1c9};
        Encrypt.ByteSub(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd}));
    }

    @Test
    public void testInvByteSub(){
        int[] state = new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd};
        Decrypt.invByteSub(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x8e9ff1c6, 0x4ddce1c7, 0xa158d1c8, 0xbc9dc1c9}));
    }

    @Test
    public void testInvertabilityByteSub(){
        int[] state = new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd};
        Encrypt.ByteSub(state);
        Decrypt.invByteSub(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x19dba1b4, 0xe386f8c6, 0x326a3ee8, 0x655e78dd}));
    }

    @Test
    public void testMixColumn(){
        int[] state = new int[]{0xdbf201c6, 0x130a01c6, 0x532201c6, 0x455c01c6};
        Encrypt.MixColumn(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6}));

    }

    @Test
    public void testInvMixColumn(){
        int[] state = new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6};
        Decrypt.invMixColumn(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0xdbf201c6, 0x130a01c6, 0x532201c6, 0x455c01c6}));
    }

    @Test
    public void testInvertabiliyMixColumn(){
        //You should get the same output as input if invertability holds
        int[] state = new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6};
        Encrypt.MixColumn(state);
        Decrypt.invMixColumn(state);
        Assertions.assertTrue(Arrays.equals(state,new int[]{0x8e9f01c6, 0x4ddc01c6, 0xa15801c6, 0xbc9d01c6}));
    }

    @Test
    public void testEncryptionDectryption(){
        byte[] testState = new byte[]{0x26, 0x26, 0x26, 0x2c, 0x54, 0x39, 0x3e, 0x26, 0x49, 0x26, 0x26, 0x26, 0x15, 0x26, 0x26, 0x26};
        byte[] compState = new byte[]{0x26, 0x26, 0x26, 0x2c, 0x54, 0x39, 0x3e, 0x26, 0x49, 0x26, 0x26, 0x26, 0x15, 0x26, 0x26, 0x26};

        byte[] testKey = sha256("password");

        Encrypt.encrypt(testState, testKey);
        Decrypt.decrypt(testState, testKey);

        Assertions.assertTrue(Arrays.equals(testState,compState));

    }

    @Test
    public void testKeyEx(){
        byte[] inKey = new byte[]{0x60,0x3D, (byte) 0xEB,0x10,};
    }
}
