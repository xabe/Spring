package com.indizen.cursoSpring.web.util.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GZIPResponseStream extends ServletOutputStream {
	protected ByteArrayOutputStream baos;
	protected GZIPOutputStream gzipOutputStream;
	protected HttpServletResponse servletResponse;
	protected ServletOutputStream outputStream;
	protected boolean closed;

	public GZIPResponseStream(HttpServletResponse httpservletresponse)
			throws IOException {
		baos = null;
		gzipOutputStream = null;
		closed = false;
		servletResponse = null;
		outputStream = null;
		closed = false;
		servletResponse = httpservletresponse;
		outputStream = httpservletresponse.getOutputStream();
		baos = new ByteArrayOutputStream();
		gzipOutputStream = new GZIPOutputStream(baos);
	}

	@Override
	public void close() throws IOException {
		if (closed) {
			throw new IOException("This output stream has already been closed");
		} else {
			gzipOutputStream.finish();
			byte abyte0[] = baos.toByteArray();
			servletResponse.addHeader("Content-Length",
					Integer.toString(abyte0.length));
			servletResponse.addHeader("Content-Encoding", "gzip");
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
			gzipOutputStream.flush();
			return;
		}
	}

	@Override
	public void write(int i) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		} else {
			gzipOutputStream.write((byte) i);
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
			gzipOutputStream.write(abyte0, i, j);
			return;
		}
	}

	public boolean closed() {
		return closed;
	}

	public void reset() {
	}
}
