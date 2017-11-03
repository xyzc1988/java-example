package com.etoak.mime4j;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.AbstractContentHandler;
import org.apache.james.mime4j.stream.BodyDescriptor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangcheng on 2017/10/26.
 */
public class MyContentHandler extends AbstractContentHandler {

    public void body(BodyDescriptor bd, InputStream is)
            throws MimeException, IOException {
        System.out.println("Body detected, contents = "
                + is + ", header data = " + bd);
    }

    public void field(String fieldData) throws MimeException {
        System.out.println("Header field detected: "
                + fieldData);
    }

    public void startMultipart(BodyDescriptor bd) throws MimeException {
        System.out.println("Multipart message detexted, header data = "
                + bd);
    }
}
