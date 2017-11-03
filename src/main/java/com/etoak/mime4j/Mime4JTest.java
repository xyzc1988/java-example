package com.etoak.mime4j;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.codec.DecoderUtil;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.EntityState;
import org.apache.james.mime4j.stream.Field;
import org.apache.james.mime4j.stream.MimeTokenStream;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangcheng on 2017/10/26 17:23.
 */
public class Mime4JTest {

    private final Logger logger = LoggerFactory.getLogger(Mime4JTest.class);

    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
    private static final Pattern PATTERN_ENCODED_WORD = Pattern.compile(
            "(.*?)=\\?(.+?)\\?(\\w)\\?(.+?)\\?=", Pattern.DOTALL);

    private static String decode1(String s) {
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb, Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }


    @Test
    public void test() throws IOException, MimeException, InvalidFormatException, URISyntaxException {
        URL resource = this.getClass().getResource("/mail/IGXE验证消息_.eml");
        InputStream newInputStream = Files.newInputStream(Paths.get(resource.toURI()));
        MimeTokenStream stream = new MimeTokenStream();
        stream.parse(newInputStream);
        for (EntityState state = stream.getState();
             state != EntityState.T_END_OF_STREAM;
             state = stream.next()) {
            switch (state) {
                case T_START_MESSAGE:
                    logger.info("T_START_MESSAGE");
                    break;
                case T_START_HEADER:
                    logger.info("T_START_HEADER");
                    break;
                case T_BODY:
                    logger.info("BODY field detected!");
                    BodyDescriptor bodyDescriptor = stream.getBodyDescriptor();
                    logger.info("bodyDescriptor : {}", bodyDescriptor);
                    String charset = bodyDescriptor.getCharset();
                    String mimeType = bodyDescriptor.getMimeType();
                    String transferEncoding = bodyDescriptor.getTransferEncoding();
                    if ("application/msword".equals(mimeType)) {
                        InputStream inputStream = stream.getDecodedInputStream();
                        WordExtractor ex = new WordExtractor(inputStream);
                        String text2003 = ex.getText();
                        System.out.println(text2003);

                       /* inputStream = stream.getDecodedInputStream();
                        FileOutputStream fos = new FileOutputStream("D:/test.doc");
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = inputStream.read(bytes)) > 0) {
                            fos.write(bytes);
                        }
                        fos.close();*/
                    } else if ("text/plain".equals(mimeType) || "text/html".equals(mimeType)) {
                        BufferedReader br = new BufferedReader(stream.getReader());
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                    break;
                case T_FIELD:
                    Field field = stream.getField();
                    String name = field.getName();
                    String body = field.getBody();
                    String chartset = "";
                    if (body.startsWith("%u")) {
                        body = body.replaceAll("%u", "\\\\u");
                        System.out.println(body);
                        body = decode1(body);
                    } else {
                        name = DecoderUtil.decodeEncodedWords(field.getName(), Charset.forName("utf-8"));
                        body = DecoderUtil.decodeEncodedWords(field.getBody(), Charset.forName("utf-8"));
                    }

                    logger.info("Header field detected: {}-------{}", name, body);
                    break;
                case T_START_MULTIPART:
                    System.out.println("Multipart message detexted,"
                            + " header data = "
                            + stream.getBodyDescriptor());
                    break;
                case T_START_BODYPART:
                    logger.info("T_START_BODYPART");
                    break;
                case T_END_BODYPART:
                    logger.info("T_END_BODYPART");
                    break;
                default:
                    //do nothing
            }
        }
    }

    @Test
    public void test1() throws IOException, MimeException {
        URL resource = this.getClass().getResource("/mail/3.eml");

        ContentHandler handler = new MyContentHandler();
        MimeStreamParser parser = new MimeStreamParser();
        parser.setContentHandler(handler);
        parser.parse(new FileInputStream(resource.getPath()));
    }

}
