package com.teradata.intenet.stream;

import java.io.*;

/**
 * SequenceInputStream 表示其他输入流的逻辑串联。
 * 它从输入流的有序集合开始，并从第一个输入流开始读取，直到到达文件末尾；
 * 接着从第二个输入流读取，依次类推，直到到达包含的最后一个输入流的文件末尾为止。
 *
 * @author Administrator
 */
public class SequenceInputStreamDemo01 {

    public static void main(String[] args) throws IOException {
        InputStream s1 = new FileInputStream("src/stream/sequence/SequenceInputStreamDemo01.java");
        InputStream s2 = new FileInputStream("src/stream/sequence/SequenceInputStreamDemo02.java");

        SequenceInputStream sis = new SequenceInputStream(s1, s2);

        InputStreamReader isr = new InputStreamReader(sis);
        BufferedReader br = new BufferedReader(isr);

        BufferedWriter bw = new BufferedWriter(new FileWriter("CopySequence.java"));

        String line = null;
        while((line = br.readLine()) != null) {
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        s1.close();
        s2.close();
        br.close();
        bw.close();
    }
}
