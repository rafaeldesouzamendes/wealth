package br.com.portopirata.wealth.wallets;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.List;

import org.bitcoinj.core.Base58;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException.MnemonicLengthException;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TronColdWalletGenerator {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
    	String passphrase = "";
        List<String> menmonic = generateWallet( passphrase );
        restoreWallet( menmonic, passphrase );
    }

    private static List<String> generateWallet( String passphrase ) throws IOException, MnemonicLengthException {
    	// 1. Gerar palavras-chave (mnemonic)
    	SecureRandom random = new SecureRandom();
    	byte[] entropy = new byte[16]; // 128 bits
    	random.nextBytes(entropy);
    	
    	MnemonicCode mc = new MnemonicCode();
    	List<String> mnemonicWords = mc.toMnemonic(entropy);
    	System.out.println("Mnemonic: " + String.join(" ", mnemonicWords));
    	
    	// 2. Criar seed e derivar chave privada usando path m/44'/195'/0'/0/0
    	DeterministicSeed seed = new DeterministicSeed(mnemonicWords, null, passphrase, System.currentTimeMillis());
    	DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
    	
    	List<org.bitcoinj.crypto.ChildNumber> path = Arrays.asList(
    			new org.bitcoinj.crypto.ChildNumber(44, true),
    			new org.bitcoinj.crypto.ChildNumber(195, true),
    			new org.bitcoinj.crypto.ChildNumber(0, true),
    			new org.bitcoinj.crypto.ChildNumber(0, false),
    			new org.bitcoinj.crypto.ChildNumber(0, false)
    			);
    	
    	DeterministicKey key = keyChain.getKeyByPath(path, true);
    	String publicKeyHex = key.getPublicKeyAsHex();
    	String privateKeyHex = key.getPrivateKeyAsHex();
    	System.out.println("Public Key: " + publicKeyHex);
    	System.out.println("Private Key: " + privateKeyHex);
    	
    	// 3. Derivar endereço TRON
    	byte[] pubKeyBytes = key.getPubKey();
    	byte[] hash = new Keccak.Digest256().digest(pubKeyBytes);
    	byte[] addressBytes = new byte[21];
    	addressBytes[0] = 0x41; // Tron prefix
    	System.arraycopy(hash, 12, addressBytes, 1, 20);
    	
    	byte[] hash0 = new Keccak.Digest256().digest(addressBytes);
    	byte[] hash1 = new Keccak.Digest256().digest(hash0);
    	byte[] checksum = new byte[4];
    	System.arraycopy(hash1, 0, checksum, 0, 4);
    	
    	byte[] fullAddress = new byte[25];
    	System.arraycopy(addressBytes, 0, fullAddress, 0, 21);
    	System.arraycopy(checksum, 0, fullAddress, 21, 4);
    	
    	String base58Address = Base58.encode(fullAddress);
    	System.out.println("TRON Address: " + base58Address);
    	
    	return mnemonicWords;
    }

    private static void restoreWallet( List<String> mnemonicWords, String passphrase ) {
    	// 🧠 Insira aqui as 12 palavras da seed gerada anteriormente
        long creationTime = 0L; // Pode ser 0 na restauração

        // 1. Gerar a seed determinística
        DeterministicSeed seed = new DeterministicSeed(mnemonicWords, null, passphrase, creationTime);

        // 2. Construir a cadeia de chaves (HD Wallet)
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();

        // 3. Caminho de derivação TRON: m/44'/195'/0'/0/0
        List<ChildNumber> path = Arrays.asList(
                new ChildNumber(44, true),
                new ChildNumber(195, true),
                new ChildNumber(0, true),
                new ChildNumber(0, false),
                new ChildNumber(0, false)
        );

        DeterministicKey key = keyChain.getKeyByPath(path, true);

        // 4. Derivar endereço TRON a partir da chave pública
        byte[] pubKeyBytes = key.getPubKey();
        byte[] hash = new Keccak.Digest256().digest(pubKeyBytes);
        byte[] addressBytes = new byte[21];
        addressBytes[0] = 0x41; // Prefixo TRON
        System.arraycopy(hash, 12, addressBytes, 1, 20);

        // 5. Gerar checksum
        byte[] hash0 = new Keccak.Digest256().digest(addressBytes);
        byte[] hash1 = new Keccak.Digest256().digest(hash0);
        byte[] checksum = Arrays.copyOfRange(hash1, 0, 4);

        // 6. Combinar tudo e gerar Base58
        byte[] fullAddress = new byte[25];
        System.arraycopy(addressBytes, 0, fullAddress, 0, 21);
        System.arraycopy(checksum, 0, fullAddress, 21, 4);
        String base58Address = Base58.encode(fullAddress);

        // 🔐 Chaves
        String publicKeyHex = key.getPublicKeyAsHex();
        String privateKeyHex = key.getPrivateKeyAsHex();

        // ✅ Exibir resultado
        System.out.println("=== RESTAURAÇÃO TRON ===");
        System.out.println("Mnemonic:      " + mnemonicWords);
        System.out.println("Public Key:    " + publicKeyHex);
        System.out.println("Private Key:   " + privateKeyHex);
        System.out.println("TRON Address:  " + base58Address);	
	}

}
