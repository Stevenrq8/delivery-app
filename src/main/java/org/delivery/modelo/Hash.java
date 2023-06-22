package org.delivery.modelo;

public class Hash {

    /**
     * Método para encriptar la contraseña con MD5 o SHA1
     *
     * @param txt      texto a encriptar
     * @param hashType tipo de encriptación
     * @return el texto encriptado
     */
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Error al encriptar contraseña: " + e.getMessage());
        }

        return null;
    }

    /**
     * Método para encriptar contraseña con MD5
     *
     * @param txt texto a encriptar
     * @return el texto encriptado con MD5
     */
    public static String md5(String txt) {
        return Hash.getHash(txt, "MD5");
    }

    /**
     * Método para encriptar contraseña con SHA1
     *
     * @param txt texto a encriptar
     * @return el texto encriptado con SHA1
     */
    public static String sha1(String txt) {
        return Hash.getHash(txt, "SHA1");
    }
}
