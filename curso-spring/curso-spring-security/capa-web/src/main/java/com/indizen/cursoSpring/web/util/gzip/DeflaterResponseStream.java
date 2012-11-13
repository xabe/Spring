package com.indizen.cursoSpring.web.util.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class DeflaterResponseStream extends ServletOutputStream {
    protected ByteArrayOutputStream baos;
    protected DeflaterOutputStream deflaterOutputStream;
    protected HttpServletResponse servletResponse;
    protected ServletOutputStream outputStream;
    protected boolean closed;

    public DeflaterResponseStream(HttpServletResponse httpservletresponse) throws IOException {
        baos = null;
        deflaterOutputStream = null;
        closed = false;
        servletResponse = null;
        outputStream = null;
        closed = false;
        servletResponse = httpservletresponse;
        outputStream = httpservletresponse.getOutputStream();
        baos = new ByteArrayOutputStream();
        deflaterOutputStream = new DeflaterOutputStream(baos);
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            throw new IOException("This output stream has already been closed");
        } else {
            deflaterOutputStream.finish();
            byte abyte0[] = baos.toByteArray();
            servletResponse.addHeader("Content-Length", Integer.toString(abyte0.length));
            servletResponse.addHeader("Content-Encoding", "deflate");
            outputStream.write(abyte0);
            outputStream.flush();
            outputStream.close();
            closed = true;
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        if (closed) {
            throw new IOException("Cannot flush a closed output stream");
        } else {
            deflaterOutputStream.flush();
            return;
        }
    }

    @Override
    public void write(int i) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        } else {
            deflaterOutputStream.write((byte) i);
            return;
        }
    }

    @Override
    public void write(byte abyte0[]) throws IOException {
        write(abyte0, 0, abyte0.length);
    }

    @Override
    public void write(byte abyte0[], int i, int j) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        } else {
            deflaterOutputStream.write(abyte0, i, j);
            return;
        }
    }

    public boolean closed() {
        return closed;
    }

    public void reset() {
    }
}